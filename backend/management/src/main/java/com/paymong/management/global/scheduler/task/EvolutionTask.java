package com.paymong.management.global.scheduler.task;

import com.paymong.management.global.client.ClientService;
import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.dto.*;
import com.paymong.management.global.exception.GatewayException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.scheduler.dto.NextLevelDto;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class EvolutionTask {
    private final MongRepository mongRepository;

    private final ClientService clientService;

//    @Transactional
//    public NextLevelDto evolutionMong2Level1(Long mongId) throws NotFoundMongException, GatewayException {
//        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
//                .orElseThrow(() -> new NotFoundMongException());
//
//        // 다음 티어 결정 - 알에서 부화하는건 필요 없다.
//        FindMongLevelCodeDto findMongLevelCodeDto = new FindMongLevelCodeDto();
//        findMongLevelCodeDto.setLevel(1);
//        findMongLevelCodeDto.setTier(0);
//
//        CommonCodeDto commonCodeDto = clientService.findMongLevelCode(findMongLevelCodeDto);
//
//        mong.setCode(commonCodeDto.getCode());
//        mong.setWeight(mong.getWeight() + 10 > 99 ? 99 : mong.getWeight() + 10);
//
//        // collect service에 새로운 몽 추가
//        clientService.addMong(String.valueOf(mong.getMemberId()),
//                new FindCommonCodeDto(commonCodeDto.getCode()));
//
//        findMongLevelCodeDto.setType(Integer.parseInt(commonCodeDto.getCode().substring(4,5)));
//
//        NextLevelDto levelDto = new NextLevelDto(mongId, findMongLevelCodeDto.getLevel(), findMongLevelCodeDto.getType());
//        return levelDto;
//    }
//
//    @Transactional
//    public NextLevelDto evolutionMong2Level2(Long mongId) throws NotFoundMongException, GatewayException {
//        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
//                .orElseThrow(() -> new NotFoundMongException());
//
//        // 다음 티어 결정 - level 1 -> 2
//        FindMongLevelCodeDto findMongLevelCodeDto = checkTierByMong(mong);
//
//        CommonCodeDto commonCodeDto = clientService.findMongLevelCode(findMongLevelCodeDto);
//
//        mong.setCode(commonCodeDto.getCode());
//        mong.setWeight(mong.getWeight() + 10 > 99 ? 99 : mong.getWeight() + 10);
//
//        // collect service에 새로운 몽 추가
//        clientService.addMong(String.valueOf(mong.getMemberId()),
//                new FindCommonCodeDto(commonCodeDto.getCode()));
//
//        NextLevelDto levelDto = new NextLevelDto(mongId, findMongLevelCodeDto.getLevel(), findMongLevelCodeDto.getType());
//        return levelDto;
//    }
//
//    @Transactional
//    public NextLevelDto evolutionMong2Level3(Long mongId) throws NotFoundMongException, GatewayException {
//        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
//                .orElseThrow(() -> new NotFoundMongException());
//
//        FindTotalPayDto findTotalPayDto = new FindTotalPayDto();
//        findTotalPayDto.setStartTime(mong.getRegDt());
//        findTotalPayDto.setEndTime(LocalDateTime.now());
//        TotalPointDto totalPointDto = clientService.findTotalPay(String.valueOf(mong.getMemberId()), findTotalPayDto);
//        // 다음 티어 결정 - level 2 -> 3
//        FindMongLevelCodeDto findMongLevelCodeDto = checkTierByPoint(mong, totalPointDto.getTotalPoint());
//
//        CommonCodeDto commonCodeDto = clientService.findMongLevelCode(findMongLevelCodeDto);
//
//        mong.setCode(commonCodeDto.getCode());
//        mong.setWeight(mong.getWeight() + 10 > 99 ? 99 : mong.getWeight() + 10);
//
//        // collect service에 새로운 몽 추가
//        clientService.addMong(String.valueOf(mong.getMemberId()),
//                new FindCommonCodeDto(commonCodeDto.getCode()));
//
//        NextLevelDto levelDto = new NextLevelDto(mongId, findMongLevelCodeDto.getLevel(), findMongLevelCodeDto.getType());
//        return levelDto;
//    }

    @Transactional
    public void evolutionMong(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
                .orElseThrow(() -> new NotFoundMongException());
        // 해당 몽 졸업
        log.info("진화 대기 상태로 들어갑니다. id : {}", mongId);
        mong.setStateCode(MongConditionCode.EVOLUTION_READY.getCode());
    }

//    private FindMongLevelCodeDto checkTierByMong(Mong mong){
//        FindMongLevelCodeDto findMongLevelCodeDto = new FindMongLevelCodeDto();
//        findMongLevelCodeDto.setLevel(2);
//        findMongLevelCodeDto.setType(Integer.parseInt(mong.getCode().substring(4,5)));
//        // 첫번째 분기 : 패널티
//        if(mong.getPenalty() < 2){
//            // 두번째 분기 : 트레이닝 횟수
//            if(mong.getTrainingCount() >= 16){
//                findMongLevelCodeDto.setTier(3);
//            }else{
//                findMongLevelCodeDto.setTier(2);
//            }
//        }else{
//            // 두번째 분기 2 : 쓰다듬은 횟수
//            if(mong.getStrokeCount() < 4){
//                findMongLevelCodeDto.setTier(0);
//                findMongLevelCodeDto.setType(3);
//            }else{
//                if(mong.getTrainingCount() >= 16){
//                    findMongLevelCodeDto.setTier(1);
//                }else{
//                    findMongLevelCodeDto.setTier(0);
//                }
//            }
//        }
//
//        return findMongLevelCodeDto;
//    }
//
//    private FindMongLevelCodeDto checkTierByPoint(Mong mong, Integer point){
//        FindMongLevelCodeDto findMongLevelCodeDto = new FindMongLevelCodeDto();
//        findMongLevelCodeDto.setLevel(3);
//        findMongLevelCodeDto.setType(Integer.parseInt(mong.getCode().substring(4,5)));
//        int tier = Integer.parseInt(mong.getCode().substring(3,4));
//
//        if(tier == 3){
//            if(point >= 50000){
//                findMongLevelCodeDto.setTier(3);
//            }else{
//                findMongLevelCodeDto.setTier(2);
//            }
//        }else if(tier == 2){
//            if(point >= 40000){
//                findMongLevelCodeDto.setTier(2);
//            }else{
//                findMongLevelCodeDto.setTier(1);
//            }
//        }else if(tier == 1){
//            if(point >= 30000){
//                findMongLevelCodeDto.setTier(1);
//            }else{
//                findMongLevelCodeDto.setTier(0);
//            }
//        }else if(tier == 0){
//            if(point >= 20000){
//                findMongLevelCodeDto.setTier(0);
//            }else{
//                findMongLevelCodeDto.setTier(0);
//                findMongLevelCodeDto.setType(3);
//            }
//        }
//
//        return findMongLevelCodeDto;
//
//    }
}

