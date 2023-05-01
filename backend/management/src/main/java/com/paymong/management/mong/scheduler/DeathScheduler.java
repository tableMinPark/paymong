package com.paymong.management.mong.scheduler;

import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.scheduler.ManagementScheduler;
import com.paymong.management.mong.service.DeathService;
import com.paymong.management.mong.service.MongService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DeathScheduler implements ManagementScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeathScheduler.class);
    private static final Map<Long, ThreadPoolTaskScheduler> schedulerMap = new HashMap<>();
    private final DeathService deathService;
    @Override
    public void stopScheduler(Long mongId) {
        if(schedulerMap.containsKey(mongId)){
            LOGGER.info("{}의 {} scheduler를 중지합니다.", this.getClass().getSimpleName(), mongId);
            schedulerMap.get(mongId).shutdown();
        }else{
            LOGGER.info("{}의 {} scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }

    }

    @Override
    public void startScheduler(Long mongId) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
//        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//      scheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        scheduler.setThreadNamePrefix("health-" + mongId + "-");
        // 스케쥴러 시작
        LOGGER.info("new {}를 추가합니다.", this.getClass().getSimpleName());
        scheduler.schedule(getRunnable(mongId), Date.from(Instant.now().plusSeconds(getDelay())));
        schedulerMap.put(mongId, scheduler);
    }

    @Override
    public Runnable getRunnable(Long mongId) {
        return () -> {
            try {
                deathService.deathMong(mongId);

            } catch (NotFoundMongException e) {
                stopScheduler(mongId);
            }

        };
    }

    @Override
    public Trigger getTrigger() {
        return null;
    }

    @Override
    public Long getDelay() {
//        return 3L * 60L * 60L;
        return  5L;
    }
}
