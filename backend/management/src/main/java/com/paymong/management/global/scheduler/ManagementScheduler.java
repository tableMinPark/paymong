package com.paymong.management.global.scheduler;

import com.paymong.management.poop.service.PoopService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public abstract class ManagementScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagementScheduler.class);
    private static final Map<Long, ThreadPoolTaskScheduler> schedulerMap = new HashMap<>();

    public void stopScheduler(Long mongId){
        schedulerMap.get(mongId).shutdown();
    }

    public void startScheduler(Long mongId){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
//      scheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);

        // 스케쥴러 시작
        LOGGER.info("new {}를 추가합니다.", this.getClass().getSimpleName());
        scheduler.schedule(getRunnable(mongId), getTrigger());
        schedulerMap.put(mongId, scheduler);
    }

//    public void addSchedulerByInstant(Long mongId){
//        LOGGER.info("new {}를 추가합니다.", this.getClass().getSimpleName());
//        scheduler.schedule(getRunnable(mongId), getInstant());
//    }

    public abstract Runnable getRunnable(Long mongId);
    public abstract Trigger getTrigger();
//    public abstract Instant getInstant();
//    private Trigger getTrigger(){
//        // 작업 주기 결정
//        return new PeriodicTrigger(2, TimeUnit.SECONDS);
//    }
}
