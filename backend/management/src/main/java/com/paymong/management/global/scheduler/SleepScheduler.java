package com.paymong.management.global.scheduler;

import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.scheduler.dto.SleepSchedulerDto;
import com.paymong.management.global.scheduler.task.SatietyTask;
import com.paymong.management.global.scheduler.task.SleepTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class SleepScheduler implements ManagementScheduler{
    private static final Map<Long, SleepSchedulerDto> schedulerMap = new HashMap<>();
    private final SleepTask sleepTask;
    private final DeathScheduler deathScheduler;
    private final HealthScheduler healthScheduler;
    private final PoopScheduler poopScheduler;
    private final SatietyScheduler satietyScheduler;

    @Override
    public void stopScheduler(Long mongId) {
        if(schedulerMap.containsKey(mongId)){
            log.info("{}의 {} scheduler를 중지합니다.", this.getClass().getSimpleName(), mongId);
            schedulerMap.get(mongId).getDynamicScheduler().shutdown();

        }else{
            log.info("{}의 {} scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }
    }

    @Override
    public void startScheduler(Long mongId) {
        if(!schedulerMap.containsKey(mongId)){
            log.info("{}이 없습니다.", mongId);
        }

    }

    public void initScheduler(Long mongId, LocalTime sleepStart, LocalTime sleepEnd){
        if(schedulerMap.containsKey(mongId)) schedulerMap.remove(mongId);
        SleepSchedulerDto schedulerDto = new SleepSchedulerDto();
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        scheduler.setThreadNamePrefix("static-sleep-");

        schedulerMap.put(mongId, schedulerDto);

        if(checkTime(sleepStart, sleepEnd)){
            scheduler.schedule(getSleepRunnable(mongId), Instant.now());
        }

        scheduler.schedule(getSleepRunnable(mongId), getCronTrigger(sleepStart));
        scheduler.schedule(getAwakeRunnable(mongId), getCronTrigger(sleepEnd));
        schedulerDto.setStaticScheduler(scheduler);



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

    @Override
    public Runnable getRunnable(Long mongId) {
        return null;
    }

    public Runnable getSleepRunnable(Long mongId){
        return ()->{
            try {
                SleepSchedulerDto schedulerDto = schedulerMap.get(mongId);
                schedulerDto.setStartTime(LocalDateTime.now());
                sleepTask.sleepMong(mongId);
                healthScheduler.stopScheduler(mongId);
                satietyScheduler.stopScheduler(mongId);
                poopScheduler.stopScheduler(mongId);
                deathScheduler.pauseScheduler(mongId);
            }catch (NotFoundMongException e){
                stopScheduler(mongId);
            }

        };
    }

    public Runnable getAwakeRunnable(Long mongId){
        return ()->{
            try {
                Duration diff = Duration.between(schedulerMap.get(mongId).getStartTime(), LocalDateTime.now());
                Long expire = diff.toMinutes();
                sleepTask.awakeMong(mongId, expire);
                healthScheduler.startScheduler(mongId);
                satietyScheduler.startScheduler(mongId);
                poopScheduler.startScheduler(mongId);
                deathScheduler.restartScheduler(mongId);
            }catch (NotFoundMongException e){
                stopScheduler(mongId);
            }

        };
    }

    @Override
    public Trigger getTrigger() {
        return null;
    }

    public Trigger getCronTrigger(LocalTime time){
        return new CronTrigger("0 "+time.getMinute()+" "+time.getHour()+" * * ?");
    }

    @Override
    public Long getDelay() {
        return null;
    }
}
