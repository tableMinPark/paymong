package com.paymong.management.global.scheduler;

import com.paymong.management.global.exception.NotFoundMongException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class StrokeScheduler implements ManagementScheduler{
    private static final Map<Long, ThreadPoolTaskScheduler> schedulerMap = new HashMap<>();
    @Override
    public void stopScheduler(Long mongId) {
        if(schedulerMap.containsKey(mongId)){
            log.info("{}의 {} scheduler를 중지합니다.", this.getClass().getSimpleName(), mongId);
            schedulerMap.get(mongId).shutdown();
            schedulerMap.remove(mongId);
        }else{
            log.info("{}의 {} scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }
    }

    @Override
    public void startScheduler(Long mongId) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        scheduler.setThreadNamePrefix("storke-" + mongId + "-");
        // 스케쥴러 시작
        log.info("new {}를 추가합니다.", this.getClass().getSimpleName());
        scheduler.schedule(getRunnable(mongId), Date.from(Instant.now().plusSeconds(getDelay())));
        schedulerMap.put(mongId, scheduler);
    }

    @Override
    public Runnable getRunnable(Long mongId) {
        return () -> {
            log.info("{} 이 Id는 이제 쓰다듬을 수 있습니다.", mongId);
            stopScheduler(mongId);
        };
    }

    @Override
    public Trigger getTrigger() {
        return null;
    }

    @Override
    public Long getDelay() {

//        return 60L * 60L;
        return 5L;
    }

    public Boolean checkMong(Long mongId){
        if(schedulerMap.containsKey(mongId)) return true;
        else return false;
    }
}
