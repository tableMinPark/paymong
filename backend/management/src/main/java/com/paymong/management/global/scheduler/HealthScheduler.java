package com.paymong.management.global.scheduler;

import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.scheduler.task.HealthTask;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class HealthScheduler implements ManagementScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthScheduler.class);
    private static final Map<Long, ThreadPoolTaskScheduler> schedulerMap = new HashMap<>();
    private static final Map<Long, ThreadPoolTaskScheduler> deathSchedulerMap = new HashMap<>();
    private final HealthTask healthTask;
    @Override
    public void stopScheduler(Long mongId) {
        if(schedulerMap.containsKey(mongId)){
            LOGGER.info("{}의 {} scheduler를 중지합니다.", this.getClass().getSimpleName(), mongId);
            schedulerMap.get(mongId).shutdown();
        }else{
            LOGGER.info("{}의 {} scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }
    }

    public void stopDeathScheduler(Long mongId){
        if(deathSchedulerMap.containsKey(mongId)){
            LOGGER.info("{}의 {} death scheduler를 중지합니다.", this.getClass().getSimpleName(), mongId);
            deathSchedulerMap.get(mongId).shutdown();
        }else{
            LOGGER.info("{}의 {} death scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
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
        scheduler.scheduleWithFixedDelay(getRunnable(mongId), Date.from(Instant.now().plusSeconds(getDelay())), getDelay() * 1000L);
        schedulerMap.put(mongId, scheduler);
    }

    public void startDeathScheduler(Long mongId){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        scheduler.setThreadNamePrefix("health-Death-" + mongId + "-");
        LOGGER.info("건강악화로 {}의 죽음 스케쥴러를 시작합니다.", this.getClass().getSimpleName());
        scheduler.schedule(getDeathRunnable(mongId),Instant.now().plusSeconds(60L * 60L * 3L));
        deathSchedulerMap.put(mongId, scheduler);
    }

    @Override
    public Runnable getRunnable(Long mongId) {
        return () -> {
            try {
                if(!healthTask.reduceHealth(mongId)){
                    stopScheduler(mongId);
                    startDeathScheduler(mongId);
                }
                ;
            } catch (NotFoundMongException e) {
                stopScheduler(mongId);
            }

        };
    }

    public Runnable getDeathRunnable(Long mongId){
        return () -> {
            try {
                healthTask.deathMong(mongId);
            }catch (NotFoundMongException e){
                stopDeathScheduler(mongId);
            }
        };
    }

    @Override
    public Trigger getTrigger() {
        return new PeriodicTrigger(15, TimeUnit.MINUTES);
    }

    @Override
    public Long getDelay() {
        return 15L * 60L;
    }
}
