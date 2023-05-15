package com.paymong.management.sleep.service;

import com.paymong.management.global.code.ManagementStateCode;
import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.scheduler.SleepScheduler;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SleepService {

    private final MongRepository mongRepository;

    private final SleepScheduler sleepScheduler;
    @Transactional
    public Boolean sleepMong(Long mongId) throws Exception{
        Mong mong = mongRepository.findByMongId(mongId).orElseThrow(() -> new NotFoundMongException());

        if(checkTime(mong.getSleepStart(),mong.getSleepEnd())){
            log.info("지금은 깨울 수 없습니다. mongId = {}", mongId);
            return false;
        }
        // 자는 상태일때 깨우기
        if(mong.getStateCode().equals(MongConditionCode.SLEEP.getCode())){
            log.info("잠 깨울 준비중입니다. mongId = {}", mongId);
            sleepScheduler.awakeScheduler(mongId);

        }else{ // 자는 상태가 아닐 땐 재우기
            log.info("잠 재울 준비중입니다. mongId = {}", mongId);
            sleepScheduler.startScheduler(mongId);
        }

        return true;
    }

    public Boolean checkTime(LocalTime sleepStart, LocalTime sleepEnd){
        LocalTime now = LocalTime.now();
        if(sleepStart.getHour() > sleepEnd.getHour()){
            // ex) 22:00 ~ 08:00
            if(now.isAfter(sleepStart) || now.isBefore(sleepEnd)){
                return true;
            }
        }else{
            // ex) 08:00 ~ 22:00
            if(now.isAfter(sleepStart) && now.isBefore(sleepEnd)){
                return true;
            }
        }
        return false;
    }
}
