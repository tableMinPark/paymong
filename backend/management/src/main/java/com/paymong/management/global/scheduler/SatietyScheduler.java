package com.paymong.management.global.scheduler;

import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.exception.UnsuitableException;
import com.paymong.management.global.scheduler.task.HealthTask;
import com.paymong.management.global.scheduler.task.SatietyTask;
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
public class SatietyScheduler implements ManagementScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SatietyScheduler.class);
    private static final Map<Long, ThreadPoolTaskScheduler> schedulerMap = new HashMap<>();
    private final SatietyTask satietyTask;
    private final DeathScheduler deathScheduler;
    @Override
    public void stopScheduler(Long mongId) {
        if(schedulerMap.containsKey(mongId)){
            LOGGER.info("{}의 {} scheduler를 중지합니다.", this.getClass().getSimpleName(), mongId);
            schedulerMap.get(mongId).shutdown();
            schedulerMap.remove(mongId);
        }else{
            LOGGER.info("{}의 {} scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }
    }

    @Override
    public void startScheduler(Long mongId) {
        if(schedulerMap.containsKey(mongId)){
            LOGGER.info("이미 굶어 있습니다. {}", mongId);
            return;
        }
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
//        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//      scheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        scheduler.setThreadNamePrefix("satiety-" + mongId + "-");
        // 스케쥴러 시작
        LOGGER.info("new {}를 추가합니다.", this.getClass().getSimpleName());
        scheduler.scheduleWithFixedDelay(getRunnable(mongId), Date.from(Instant.now().plusSeconds(getDelay())), getDelay() * 1000L);
        schedulerMap.put(mongId, scheduler);
    }


    @Override
    public Runnable getRunnable(Long mongId) {
        return () -> {
            try {
                if(!satietyTask.reduceSatiety(mongId)){
                    stopScheduler(mongId);
                    deathScheduler.startScheduler(mongId);
                }
                ;
            }catch (NotFoundMongException e) {
                LOGGER.info("없는 몽입니다. mongId : {}" ,mongId);
                stopScheduler(mongId);
            }catch (UnsuitableException e) {
                LOGGER.info("옳지 않은 몽입니다. mongId : {}" ,mongId);
                stopScheduler(mongId);
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
