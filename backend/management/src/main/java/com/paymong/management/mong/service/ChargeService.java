package com.paymong.management.mong.service;

import com.paymong.management.global.code.MongActiveCode;
import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.code.WebSocketCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.socket.service.WebSocketService;
import com.paymong.management.history.entity.ActiveHistory;
import com.paymong.management.history.repository.ActiveHistoryRepository;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChargeService {

    private final MongRepository mongRepository;
    private final WebSocketService webSocketService;

    private final ActiveHistoryRepository activeHistoryRepository;

    private final StatusService statusService;

    private static final Map<Long, LocalDateTime> startMap = new HashMap<>();

    @Transactional
    public void charging(Long memberId) throws NotFoundMongException {
        Mong mong = mongRepository.findByMemberIdAndActive(memberId, true)
                .orElseThrow(()-> new NotFoundMongException());

        mong.setStateCode(MongConditionCode.CHARGING.getCode());

        startMap.put(memberId, LocalDateTime.now());

        ActiveHistory activeHistory = ActiveHistory.builder()
                .activeCode(MongActiveCode.CHARGING.getCode())
                .activeTime(LocalDateTime.now())
                .mongId(mong.getMongId())
                .build();

        activeHistoryRepository.save(activeHistory);

        webSocketService.sendStatus(mong, WebSocketCode.SUCCESS);
    }

    @Transactional
    public void discharging(Long memberId) throws NotFoundMongException {
        if(!startMap.containsKey(memberId)){
            log.info("충전중이 아닙니다. memberId : {}", memberId);
            return;
        }
        Mong mong = mongRepository.findByMemberIdAndActive(memberId, true)
                .orElseThrow(()-> new NotFoundMongException());


        Integer level = Integer.parseInt(mong.getCode().substring(2,3));
        Integer tier = Integer.parseInt(mong.getCode().substring(3,4));

        Duration diff = Duration.between(startMap.get(memberId), LocalDateTime.now());
        Long expire = diff.toMinutes();
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
        }

        ActiveHistory activeHistory = ActiveHistory.builder()
                .activeCode(MongActiveCode.DISCHARGING.getCode())
                .activeTime(LocalDateTime.now())
                .mongId(mong.getMongId())
                .build();

        activeHistoryRepository.save(activeHistory);

        MongConditionCode condition = statusService.checkCondition(mong);
        log.info("{}의 충전을 멈춥니다. 상태 : {}", mong.getMongId(), condition.getMessage());
        mong.setStateCode(condition.getCode());

        startMap.remove(memberId);
        webSocketService.sendStatus(mong, WebSocketCode.SUCCESS);
    }
}
