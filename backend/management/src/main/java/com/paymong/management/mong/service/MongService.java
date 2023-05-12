package com.paymong.management.mong.service;

import com.paymong.management.global.client.ClientService;
import com.paymong.management.global.code.MongActiveCode;
import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.dto.*;
import com.paymong.management.global.exception.*;
import com.paymong.management.global.scheduler.EvolutionScheduler;
import com.paymong.management.global.scheduler.dto.NextLevelDto;
import com.paymong.management.global.scheduler.service.SchedulerService;
import com.paymong.management.history.entity.ActiveHistory;
import com.paymong.management.history.repository.ActiveHistoryRepository;
import com.paymong.management.mong.dto.EvolutionMongResDto;
import com.paymong.management.mong.dto.GraduationMongResDto;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.mong.vo.AddMongReqVo;
import com.paymong.management.mong.vo.AddMongResVo;
import com.paymong.management.mong.vo.FindMongReqVo;
import com.paymong.management.mong.vo.FindMongResVo;
import com.paymong.management.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MongService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongService.class);
    private final MongRepository mongRepository;
    private final ClientService clientService;
    private final SchedulerService schedulerService;
    private final StatusService statusService;
    private final EvolutionScheduler evolutionScheduler;
    private final ActiveHistoryRepository activeHistoryRepository;

    @Transactional
    public AddMongResVo addMong(AddMongReqVo addMongReqVo) throws Exception{

        Optional<Mong> chkMong = mongRepository.findByMemberIdAndActive(addMongReqVo.getMemberId(), true);
        if(chkMong.isPresent()){
            // 이미 있는데 그친구가 DIE가 아닌 경우 에러 처리
            if(!chkMong.get().getStateCode().equals(MongConditionCode.DIE.getCode())){
                throw new AlreadyExistMongException();
            }else{
                // 이미 있지만 DIE인 경우 비활성화 하고 새로 생성
                chkMong.get().setActive(false);
            }
        }
        FindMongLevelCodeDto findMongLevelCodeDto = new FindMongLevelCodeDto();
        findMongLevelCodeDto.setLevel(0);

        CommonCodeDto commonCodeDto = clientService.findMongLevelCode(findMongLevelCodeDto);

        clientService.addMong(String.valueOf(addMongReqVo.getMemberId()),
                new FindCommonCodeDto(commonCodeDto.getCode()));

        Mong mong = Mong.builder()
                .name(addMongReqVo.getName())
                .memberId(addMongReqVo.getMemberId())
                .code(commonCodeDto.getCode())
                .sleepStart(addMongReqVo.getSleepStart())
                .sleepEnd(addMongReqVo.getSleepEnd())
                .build();

        Mong newMong = mongRepository.save(mong);

        AddMongResVo addMongResVo = new AddMongResVo(newMong);
        // 무슨 이유인진 몰라도 null로 처리됨..
        addMongResVo.setWeight(5);
        addMongResVo.setBorn(LocalDateTime.now());

        return addMongResVo;
    }

    @Transactional
    public FindMongResVo findMongByMember(FindMongReqVo findMongReqVo) throws Exception{
        Mong mong = mongRepository.findByMemberIdAndActive(findMongReqVo.getMemberId(), true)
                .orElseThrow(()-> new NotFoundMongException());

        FindMongResVo findMongResVo = new FindMongResVo();
        findMongResVo.setMongId(mong.getMongId());
        findMongResVo.setMongCode(mong.getCode());
        return findMongResVo;
    }

    @Transactional
    public void startScheduler(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
                .orElseThrow(()-> new NotFoundMongException());
        schedulerService.startScheduler(mong);
    }

    @Transactional
    public EvolutionMongResDto evolutionMong(Long mongId) throws NotFoundMongException, GatewayException, UnsuitableException {
        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
                .orElseThrow(()-> new NotFoundMongException());

        if(!mong.getStateCode().equals(MongConditionCode.EVOLUTION_READY.getCode())){
            throw new UnsuitableException();
        }

        boolean ok = false;
        Integer level = Integer.parseInt(mong.getCode().substring(2,3));

        if(level == 0){
            FindMongLevelCodeDto findMongLevelCodeDto = new FindMongLevelCodeDto();
            findMongLevelCodeDto.setLevel(1);
            findMongLevelCodeDto.setTier(0);

            CommonCodeDto commonCodeDto = clientService.findMongLevelCode(findMongLevelCodeDto);

            mong.setCode(commonCodeDto.getCode());
            mong.setWeight(mong.getWeight() + 10 > 99 ? 99 : mong.getWeight() + 10);

            // collect service에 새로운 몽 추가
            clientService.addMong(String.valueOf(mong.getMemberId()),
                    new FindCommonCodeDto(commonCodeDto.getCode()));

            findMongLevelCodeDto.setType(Integer.parseInt(commonCodeDto.getCode().substring(4,5)));

            NextLevelDto levelDto = new NextLevelDto(mongId, findMongLevelCodeDto.getLevel(), findMongLevelCodeDto.getType());
            evolutionScheduler.nextLevelScheduler(levelDto);
        }else if(level == 1){
            // 다음 티어 결정 - level 1 -> 2
            FindMongLevelCodeDto findMongLevelCodeDto = checkTierByMong(mong);

            CommonCodeDto commonCodeDto = clientService.findMongLevelCode(findMongLevelCodeDto);

            // collect service에 새로운 몽 추가
            clientService.addMong(String.valueOf(mong.getMemberId()),
                    new FindCommonCodeDto(commonCodeDto.getCode()));

            mong.setCode(commonCodeDto.getCode());
            mong.setWeight(mong.getWeight() + 10 > 99 ? 99 : mong.getWeight() + 10);

            if(findMongLevelCodeDto.getType() == 3){
                // 타입 3이면 졸업
                mong.setStateCode(MongConditionCode.GRADUATE.getCode());
            }else{
                NextLevelDto levelDto = new NextLevelDto(mongId, findMongLevelCodeDto.getLevel(), findMongLevelCodeDto.getType());
                evolutionScheduler.nextLevelScheduler(levelDto);
            }

        }else if(level == 2){
            FindTotalPayDto findTotalPayDto = new FindTotalPayDto();
            findTotalPayDto.setStartTime(mong.getRegDt());

            LocalDateTime formattedDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.parse(formattedDateTime.format(formatter), formatter);

            LOGGER.info("{}   {}",mong.getRegDt(), now);
            findTotalPayDto.setEndTime(now);

            TotalPointDto totalPointDto = clientService.findTotalPay(String.valueOf(mong.getMemberId()), findTotalPayDto);
            // 다음 티어 결정 - level 2 -> 3
            FindMongLevelCodeDto findMongLevelCodeDto = checkTierByPoint(mong, totalPointDto.getTotalPoint());

            CommonCodeDto commonCodeDto = clientService.findMongLevelCode(findMongLevelCodeDto);

            mong.setCode(commonCodeDto.getCode());
            mong.setWeight(mong.getWeight() + 10 > 99 ? 99 : mong.getWeight() + 10);

            // collect service에 새로운 몽 추가
            clientService.addMong(String.valueOf(mong.getMemberId()),
                    new FindCommonCodeDto(commonCodeDto.getCode()));

            if(findMongLevelCodeDto.getType() == 3){
                // 타입 3이면 졸업
                mong.setStateCode(MongConditionCode.GRADUATE.getCode());
            }else{
                NextLevelDto levelDto = new NextLevelDto(mongId, findMongLevelCodeDto.getLevel(), findMongLevelCodeDto.getType());
                evolutionScheduler.nextLevelScheduler(levelDto);
            }

        }else{
            // 해당 몽 졸업
            ok = true;
            mong.setStateCode(MongConditionCode.GRADUATE.getCode());
        }

        if(!ok){
            if(!mong.getCode().substring(4,5).equals("3")){
                MongConditionCode condition = statusService.checkCondition(mong);
                mong.setStateCode(condition.getCode());
            }

            ActiveHistory activeHistory = ActiveHistory.builder()
                    .activeCode(MongActiveCode.EVOLUTION.getCode())
                    .activeTime(LocalDateTime.now())
                    .mongId(mongId)
                    .build();

            activeHistoryRepository.save(activeHistory);
        }

        EvolutionMongResDto mongResDto = new EvolutionMongResDto();
        mongResDto.setWeight(mong.getWeight());
        mongResDto.setMongCode(mong.getCode());
        mongResDto.setStateCode(mong.getStateCode());

        return mongResDto;
    }

    @Transactional
    public GraduationMongResDto graduationMong(Long mongId) throws NotFoundMongException, UnsuitableException {
        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
                .orElseThrow(()-> new NotFoundMongException());

        if(!mong.getStateCode().equals(MongConditionCode.GRADUATE.getCode())){
            throw new UnsuitableException();
        }

        mong.setCode("CH444");
        mong.setActive(false);

        ActiveHistory activeHistory = ActiveHistory.builder()
                .activeCode(MongActiveCode.GRADUATION.getCode())
                .activeTime(LocalDateTime.now())
                .mongId(mongId)
                .build();

        activeHistoryRepository.save(activeHistory);

        return new GraduationMongResDto(mong.getCode());
    }

    private FindMongLevelCodeDto checkTierByMong(Mong mong){
        FindMongLevelCodeDto findMongLevelCodeDto = new FindMongLevelCodeDto();
        findMongLevelCodeDto.setLevel(2);
        findMongLevelCodeDto.setType(Integer.parseInt(mong.getCode().substring(4,5)));
        // 첫번째 분기 : 패널티
        if(mong.getPenalty() < 2){
            // 두번째 분기 : 트레이닝 횟수
            if(mong.getTrainingCount() >= 16){
                findMongLevelCodeDto.setTier(3);
            }else{
                findMongLevelCodeDto.setTier(2);
            }
        }else{
            // 두번째 분기 2 : 쓰다듬은 횟수
            if(mong.getStrokeCount() < 4){
                findMongLevelCodeDto.setTier(0);
                findMongLevelCodeDto.setType(3);
            }else{
                if(mong.getTrainingCount() >= 16){
                    findMongLevelCodeDto.setTier(1);
                }else{
                    findMongLevelCodeDto.setTier(0);
                }
            }
        }

        return findMongLevelCodeDto;
    }

    private FindMongLevelCodeDto checkTierByPoint(Mong mong, Integer point){
        FindMongLevelCodeDto findMongLevelCodeDto = new FindMongLevelCodeDto();
        findMongLevelCodeDto.setLevel(3);
        findMongLevelCodeDto.setType(Integer.parseInt(mong.getCode().substring(4,5)));
        int tier = Integer.parseInt(mong.getCode().substring(3,4));

        if(tier == 3){
            if(point >= 50000){
                findMongLevelCodeDto.setTier(3);
            }else{
                findMongLevelCodeDto.setTier(2);
            }
        }else if(tier == 2){
            if(point >= 40000){
                findMongLevelCodeDto.setTier(2);
            }else{
                findMongLevelCodeDto.setTier(1);
            }
        }else if(tier == 1){
            if(point >= 30000){
                findMongLevelCodeDto.setTier(1);
            }else{
                findMongLevelCodeDto.setTier(0);
            }
        }else if(tier == 0){
            if(point >= 20000){
                findMongLevelCodeDto.setTier(0);
            }else{
                findMongLevelCodeDto.setTier(0);
                findMongLevelCodeDto.setType(3);
            }
        }

        return findMongLevelCodeDto;

    }
}
