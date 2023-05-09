package com.paymong.management.mong.service;

import com.paymong.management.global.client.ClientService;
import com.paymong.management.global.client.CommonServiceClient;
import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.dto.CommonCodeDto;
import com.paymong.management.global.dto.FindCommonCodeDto;
import com.paymong.management.global.dto.FindMongLevelCodeDto;
import com.paymong.management.global.exception.AlreadyExistMongException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.scheduler.service.SchedulerService;
import com.paymong.management.mong.dto.FindRandomEggDto;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.mong.vo.AddMongReqVo;
import com.paymong.management.mong.vo.AddMongResVo;
import com.paymong.management.mong.vo.FindMongReqVo;
import com.paymong.management.mong.vo.FindMongResVo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MongService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongService.class);
    private final MongRepository mongRepository;
    private final ClientService clientService;
    private final SchedulerService schedulerService;

    @Transactional
    public AddMongResVo addMong(AddMongReqVo addMongReqVo) throws Exception{

        Optional<Mong> chkMong = mongRepository.findByMemberIdAndActive(addMongReqVo.getMemberId(), true);
        if(chkMong.isPresent()){
            // 이미 있는데 그친구가 DIE가 아닌 경우 에러 처리
            if(!chkMong.get().getStateCode().equals(MongConditionCode.DIE)){
                throw new AlreadyExistMongException();
            }else{
                // 이미 있지만 DIE인 경우 비활성화 하고 새로 생성
                chkMong.get().setActive(false);
            }
        }
        FindMongLevelCodeDto findMongLevelCodeDto = new FindMongLevelCodeDto();
        findMongLevelCodeDto.setLevel(0);

        CommonCodeDto commonCodeDto = clientService.findMongLevelCode(findMongLevelCodeDto);
        Mong mong = Mong.builder()
                .name(addMongReqVo.getName())
                .memberId(addMongReqVo.getMemberId())
                .code(commonCodeDto.getCode())
                .sleepStart(addMongReqVo.getSleepStart())
                .sleepEnd(addMongReqVo.getSleepEnd())
                .build();

        Mong newMong = mongRepository.save(mong);

        clientService.addMong(String.valueOf(addMongReqVo.getMemberId()),
                new FindCommonCodeDto(commonCodeDto.getCode()));

        AddMongResVo addMongResVo = new AddMongResVo(newMong);
        // 무슨 이유인진 몰라도 null로 처리됨..
        addMongResVo.setWeight(5);
        addMongResVo.setBorn(LocalDateTime.now());

        // 스케줄러 활성화... 굳이 알에서 ?
        // 빨리 진화를 짜야겠구만유
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


}
