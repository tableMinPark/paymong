package com.paymong.management.sleep.service;

import com.paymong.management.global.code.ManagementStateCode;
import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SleepService {

    private final MongRepository mongRepository;
    private final StatusService statusService;
    @Transactional
    public void sleepMong(Long mongId) throws Exception{
        Mong mong = mongRepository.findByMongId(mongId).orElseThrow(() -> new NotFoundMongException());

        // 자는 상태일때 깨우기
        if(mong.getStateCode().equals(MongConditionCode.SLEEP.getCode())){
            //상태 체크하기 - 함수 만들기
            MongConditionCode condition = statusService.checkCondition(mong);
            log.info("{}의 잠을 깨웁니다. 상태 : {}",mongId, condition.getMessage());
            mong.setStateCode(condition.getCode());
        }else{ // 자는 상태가 아닐 땐 재우기
            log.info("{}의 잠을 재웁니다. 이전 상태 : {}",mongId, MongConditionCode.codeOf(mong.getStateCode()).getMessage());
            mong.setStateCode(MongConditionCode.SLEEP.getCode());
        }
    }
}
