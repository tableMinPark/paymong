package com.paymong.management.global.scheduler.task;

import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SleepTask {

    private final MongRepository mongRepository;
    private final StatusService statusService;

    @Transactional
    public void sleepMong(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findByMongId(mongId)
                .orElseThrow(() -> new NotFoundMongException());

        log.info("{}의 잠을 재웁니다. 이전 상태 : {}",mongId, MongConditionCode.codeOf(mong.getStateCode()).getMessage());
        mong.setStateCode(MongConditionCode.SLEEP.getCode());
    }

    @Transactional
    public void awakeMong(Long mongId, Long expire) throws NotFoundMongException {
        // expire 단위 분
        Mong mong = mongRepository.findByMongId(mongId)
                .orElseThrow(() -> new NotFoundMongException());
        // 3시간 이상일 경우
        if(expire >= 180L){
            // 풀피
        }else{
            Integer level = Integer.parseInt(mong.getCode().substring(2,3));
        }



        MongConditionCode condition = statusService.checkCondition(mong);
        log.info("{}의 잠을 깨웁니다. 상태 : {}",mongId, condition.getMessage());
        mong.setStateCode(condition.getCode());
    }

}
