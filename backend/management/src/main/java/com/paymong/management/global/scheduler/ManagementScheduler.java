package com.paymong.management.global.scheduler;

import com.paymong.management.poop.service.PoopService;
import lombok.Data;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public interface ManagementScheduler {

//    public void stopScheduler(Long mongId){
//        LOGGER.info("{}의 {} scheduler를 중지합니다.", this.getClass().getSimpleName(), mongId);
//        schedulerMap.get(mongId).shutdown();
//    }
//
//    public void startScheduler(Long mongId){
//        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
////        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.initialize();
////      scheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
//
//        // 스케쥴러 시작
//        LOGGER.info("new {}를 추가합니다.", this.getClass().getSimpleName());
////        scheduler.schedule(getRunnable(mongId), getTrigger());
//        schedulerMap.put(mongId, scheduler);
//    }

//    public void startScheduler(Long mongId){
//        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
//        scheduler.initialize();
////        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
////      scheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
//        scheduler.setThreadNamePrefix("hello-" + mongId + "-");
//        // 스케쥴러 시작
//        LOGGER.info("new {}를 추가합니다.", this.getClass().getSimpleName());
//        scheduler.scheduleWithFixedDelay(getRunnable(mongId), Date.from(Instant.now().plusSeconds(30L)), 5L*1000L);
//        schedulerMap.put(mongId, scheduler);
//    }

//    public void addSchedulerByInstant(Long mongId){
//        LOGGER.info("new {}를 추가합니다.", this.getClass().getSimpleName());
//        scheduler.schedule(getRunnable(mongId), getInstant());
//    }

    void stopScheduler(Long mongId);

    void startScheduler(Long mongId);

    Runnable getRunnable(Long mongId);
    Trigger getTrigger();
    Long getDelay();

}
