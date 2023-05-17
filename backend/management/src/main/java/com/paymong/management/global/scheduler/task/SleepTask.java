package com.paymong.management.global.scheduler.task;

import com.paymong.management.global.code.MongActiveCode;
import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.code.WebSocketCode;
import com.paymong.management.global.exception.EvolutionReadyException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.socket.service.WebSocketService;
import com.paymong.management.history.entity.ActiveHistory;
import com.paymong.management.history.repository.ActiveHistoryRepository;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.mong.service.ChargeService;
import com.paymong.management.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class SleepTask {

    private final MongRepository mongRepository;
    private final StatusService statusService;
    private final ActiveHistoryRepository activeHistoryRepository;
    private final WebSocketService webSocketService;
    private final ChargeService chargeService;

    @Transactional
    public void sleepMong(Long mongId) throws NotFoundMongException, EvolutionReadyException {
        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
                .orElseThrow(() -> new NotFoundMongException());

        if(mong.getStateCode().equals(MongConditionCode.EVOLUTION_READY.getCode())){
            throw new EvolutionReadyException();
        }
        log.info("{}의 잠을 재웁니다. 이전 상태 : {}",mongId, MongConditionCode.codeOf(mong.getStateCode()).getMessage());

        chargeService.discharging(mong.getMemberId());
        mong.setStateCode(MongConditionCode.SLEEP.getCode());

        ActiveHistory activeHistory = ActiveHistory.builder()
                .activeCode(MongActiveCode.SLEEP.getCode())
                .activeTime(LocalDateTime.now())
                .mongId(mongId)
                .build();

        activeHistoryRepository.save(activeHistory);

        webSocketService.sendStatus(mong, WebSocketCode.SUCCESS);
    }

    @Transactional
    public void awakeMong(Long mongId, Long expire) throws NotFoundMongException {
        // expire 단위 분
        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
                .orElseThrow(() -> new NotFoundMongException());

        Integer level = Integer.parseInt(mong.getCode().substring(2,3));
        Integer tier = Integer.parseInt(mong.getCode().substring(3,4));
        // 3시간 이상일 경우
        int gauge = 0;
        if(expire >= 180L){
            // 풀피
            if(level == 2){
                if(tier == 1){
                    gauge = 30;
                }else if(tier == 2){
                    gauge = 35;
                }else if(tier == 3){
                    gauge = 40;
                }else{
                    gauge = 25;
                }

            }else if(level == 3){
                if(tier == 1){
                    gauge = 40;
                }else if(tier == 2){
                    gauge = 45;
                }else if(tier == 3){
                    gauge = 50;
                }else{
                    gauge = 35;
                }
            }else {
                gauge = 20;
            }
            mong.setHealth(gauge);
            mong.setSleep(gauge);
        }else{
            double scale = 0;
            if(level == 2){
                if(tier == 1){
                    scale = 30.0/240.0;
                }else if(tier == 2){
                    scale = 35.0/240.0;
                }else if(tier == 3){
                    scale = 40.0/240.0;
                }else{
                    scale = 25.0/240.0;
                }
            }else if(level == 3){
                if(tier == 1){
                    scale = 30.0/240.0;
                }else if(tier == 2){
                    scale = 35.0/240.0;
                }else if(tier == 3){
                    scale = 40.0/240.0;
                }else{
                    scale = 25.0/240.0;
                }
            }else {
                scale = 20.0/240.0;
            }
            gauge = Integer.parseInt(String.valueOf(Math.round(scale * expire)));
            mong.setHealth(mong.getHealth() + gauge);
            mong.setSleep(mong.getSleep() + gauge);
        }

        ActiveHistory activeHistory = ActiveHistory.builder()
                .activeCode(MongActiveCode.AWAKE.getCode())
                .activeTime(LocalDateTime.now())
                .mongId(mongId)
                .build();

        activeHistoryRepository.save(activeHistory);

        MongConditionCode condition = statusService.checkCondition(mong);
        log.info("{}의 잠을 깨웁니다. 상태 : {}",mongId, condition.getMessage());
        mong.setStateCode(condition.getCode());

        webSocketService.sendStatus(mong, WebSocketCode.SUCCESS);
    }

    @Transactional
    public void minusSleep(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
                .orElseThrow(() -> new NotFoundMongException());

        log.info("{}이 졸려 집니다.",mongId);
        Integer sleep = mong.getSleep();
        mong.setSleep(sleep - 1 < 0 ? 0 : sleep - 1);
        if(sleep - 1 < 2){
            if(mong.getStateCode().equals(MongConditionCode.NORMAL.getCode())
                    || mong.getStateCode().equals(MongConditionCode.HUNGRY.getCode())){
                mong.setStateCode(MongConditionCode.SOMNOLENCE.getCode());
            }
            webSocketService.sendStatus(mong, WebSocketCode.SOMNOLENCE);
        }else{
            webSocketService.sendStatus(mong, WebSocketCode.SUCCESS);
        }

    }

}
