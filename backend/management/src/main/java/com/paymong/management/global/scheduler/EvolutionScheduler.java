package com.paymong.management.global.scheduler;

import com.paymong.management.global.scheduler.dto.SchedulerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class EvolutionScheduler implements ManagementScheduler {

    private static final Map<Long, SchedulerDto> schedulerMap = new HashMap<>();
    @Override
    public void stopScheduler(Long mongId) {
        if(schedulerMap.containsKey(mongId)){
            log.info("{}의 {} death scheduler를 중지합니다.", this.getClass().getSimpleName(), mongId);
            schedulerMap.get(mongId).getScheduler().shutdown();
            schedulerMap.remove(mongId);
        }else{
            log.info("{}의 {} death scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }
    }

    @Override
    public void startScheduler(Long mongId) {

    }

    @Override
    public Runnable getRunnable(Long mongId) {
        return null;
    }

    @Override
    public Trigger getTrigger() {
        return null;
    }

    @Override
    public Long getDelay() {
        return null;
    }
}
