package com.paymong.management.global.scheduler;

import com.paymong.management.global.exception.EvolutionReadyException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.redis.RedisMong;
import com.paymong.management.global.redis.RedisService;
import com.paymong.management.global.scheduler.dto.SchedulerDto;
import com.paymong.management.global.scheduler.dto.SleepSchedulerDto;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class SleepScheduler{
    private static final Map<Long, SleepSchedulerDto> staticSchedulerMap = new HashMap<>();

    private static final Map<Long, SchedulerDto> dynamicSchedulerMap = new HashMap<>();

    private static final Map<Long, ThreadPoolTaskScheduler> minusSchedulerMap = new HashMap<>();
    private final SleepTask sleepTask;
    private final DeathScheduler deathScheduler;
    private final HealthScheduler healthScheduler;
    private final PoopScheduler poopScheduler;
    private final SatietyScheduler satietyScheduler;
    private final EvolutionScheduler evolutionScheduler;
    private final RedisService redisService;

    public void stopScheduler(Long mongId) {
        if(staticSchedulerMap.containsKey(mongId)){
            log.info("{}의 {} static sleep scheduler를 중지합니다.", this.getClass().getSimpleName(), mongId);
            staticSchedulerMap.get(mongId).getScheduler().shutdown();
            staticSchedulerMap.remove(mongId);

        }else{
            log.info("{}의 {} static sleep scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }

        if(dynamicSchedulerMap.containsKey(mongId)){
            log.info("{}의 {} dynamic sleep scheduler를 중지합니다.", this.getClass().getSimpleName(), mongId);
            dynamicSchedulerMap.get(mongId).getScheduler().shutdown();
            dynamicSchedulerMap.remove(mongId);

        }else{
            log.info("{}의 {} dynamic sleep scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }

        if(minusSchedulerMap.containsKey(mongId)){
            log.info("{}의 {} minus sleep scheduler를 중지합니다.", this.getClass().getSimpleName(), mongId);
            minusSchedulerMap.get(mongId).shutdown();
            minusSchedulerMap.remove(mongId);

        }else{
            log.info("{}의 {} minus sleep scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }

    }

    public void stopMinusScheduler(Long mongId){
        if(minusSchedulerMap.containsKey(mongId)){
            log.info("{}의 {} minus sleep scheduler를 중지합니다.", this.getClass().getSimpleName(), mongId);
            minusSchedulerMap.get(mongId).shutdown();
            minusSchedulerMap.remove(mongId);

        }else{
            log.info("{}의 {} minus sleep scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }
    }
    public void awakeScheduler(Long mongId){

        if(!dynamicSchedulerMap.containsKey(mongId)){
           log.info("{}는 자고있지 않습니다.");
           return;
        }
        try
        {
            Duration diff = Duration.between(dynamicSchedulerMap.get(mongId).getStartTime(), LocalDateTime.now());
            Long expire = diff.toMinutes();
            log.info("mongId : {}, sleepStart : {}, expire : {}", mongId, dynamicSchedulerMap.get(mongId).getStartTime(), expire);
            sleepTask.awakeMong(mongId, expire);
            healthScheduler.startScheduler(mongId);
            satietyScheduler.startScheduler(mongId);
            poopScheduler.startScheduler(mongId);
            deathScheduler.restartScheduler(mongId);
            evolutionScheduler.restartScheduler(mongId);

            dynamicSchedulerMap.get(mongId).getScheduler().shutdown();
            dynamicSchedulerMap.remove(mongId);

            minusScheduler(mongId);

        }catch (NotFoundMongException e){
            log.info("{}의 몽이 없습니다.", mongId);
            stopScheduler(mongId);
        }
    }

    public void sleepScheduler(Long mongId) {
        if(dynamicSchedulerMap.containsKey(mongId)){
            log.info("{}는 이미 자고 있습니다.", mongId);
            return;
        }

        getSleepRunnable(mongId).run();

        log.info("{} : dynamic sleep scheduler를 추가합니다. mongId : {}", this.getClass().getSimpleName(), mongId);

        SchedulerDto schedulerDto = new SchedulerDto();
        schedulerDto.setStartTime(LocalDateTime.now());
        schedulerDto.setExpire(60L * 60L * 3L);
        schedulerDto.setMessage("dynamic-sleep-"+mongId);
        schedulerDto.setRunnable(getAwakeRunnable(mongId,1));
        schedulerDto.initScheduler();

        dynamicSchedulerMap.put(mongId, schedulerDto);
    }

    public void resleepScheduler(RedisMong redisMong) {
        if(dynamicSchedulerMap.containsKey(redisMong.getMongId())){
            log.info("{}는 이미 자고 있습니다.", redisMong.getMongId());
            return;
        }

        getSleepRunnable(redisMong.getMongId()).run();

        log.info("{} : dynamic sleep scheduler를 추가합니다. mongId : {}", this.getClass().getSimpleName(), redisMong.getMongId());

        SchedulerDto schedulerDto = new SchedulerDto();
        schedulerDto.setStartTime(LocalDateTime.now());
        schedulerDto.setExpire(redisMong.getExpire() * 60L);
        schedulerDto.setMessage("dynamic-sleep-"+redisMong.getMongId());
        schedulerDto.setRunnable(getAwakeRunnable(redisMong.getMongId(),1));
        schedulerDto.initScheduler();

        dynamicSchedulerMap.put(redisMong.getMongId(), schedulerDto);
    }

    public void addRedis(){
        redisService.addRedis(dynamicSchedulerMap, "sleep");
    }

    public void initScheduler(Long mongId, LocalTime sleepStart, LocalTime sleepEnd){
        if(staticSchedulerMap.containsKey(mongId)) staticSchedulerMap.remove(mongId);
        SleepSchedulerDto schedulerDto = new SleepSchedulerDto();
        schedulerDto.setSleepStart(sleepStart);
        schedulerDto.setSleepEnd(sleepEnd);

        Duration diff = Duration.between(sleepStart, sleepEnd);
        Long expire = diff.toMinutes();

        schedulerDto.setExpire(expire);

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        scheduler.setThreadNamePrefix("static-sleep-"+mongId);

        if(checkTime(sleepStart, sleepEnd)){
            log.info("자는 시간이라 바로 잡니다. mongId : {}", mongId);
            scheduler.schedule(getSleepRunnable(mongId), Instant.now());
        }
        log.info("{} : static sleep scheduler를 추가합니다. mongId : {}", this.getClass().getSimpleName(), mongId);
        scheduler.schedule(getSleepRunnable(mongId), getCronTrigger(sleepStart));
        scheduler.schedule(getAwakeRunnable(mongId,0), getCronTrigger(sleepEnd));

        schedulerDto.setScheduler(scheduler);

        staticSchedulerMap.put(mongId, schedulerDto);
    }

    public void minusScheduler(Long mongId){
        if(!minusSchedulerMap.containsKey(mongId)){
            log.info("{}이 없습니다.", mongId);
            return;
        }

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        scheduler.setThreadNamePrefix("minus-sleep-" + mongId);
        log.info("new {} minus Scheduler를 추가합니다. mongId : {}", this.getClass().getSimpleName(), mongId);
        scheduler.scheduleWithFixedDelay(getRunnable(mongId), Date.from(Instant.now().plusSeconds(getDelay())), getDelay() * 1000L);

        minusSchedulerMap.put(mongId, scheduler);
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

    public Runnable getRunnable(Long mongId) {

        return () -> {
            try {
                sleepTask.minusSleep(mongId);
            }catch (NotFoundMongException e){
                stopScheduler(mongId);
            }
        };
    }

    public Runnable getSleepRunnable(Long mongId){
        return ()->{
            try {
                sleepTask.sleepMong(mongId);
                healthScheduler.stopScheduler(mongId);
                satietyScheduler.stopScheduler(mongId);
                poopScheduler.stopScheduler(mongId);
                deathScheduler.pauseScheduler(mongId);
                evolutionScheduler.pauseScheduler(mongId);
                stopMinusScheduler(mongId);
            }catch (NotFoundMongException e){
                stopScheduler(mongId);
            }catch (EvolutionReadyException e){
                log.info("진화 대기 상태이므로 잘 수 없습니다. mongId : {}", mongId);
            }

        };
    }

    public Runnable getAwakeRunnable(Long mongId, Integer type){
        return ()->{
            try {
                Long expire = 0L;
                if(type == 0){
                   expire = staticSchedulerMap.get(mongId).getExpire();
                }else{
                    Duration diff = Duration.between(LocalDateTime.now(), dynamicSchedulerMap.get(mongId).getStartTime());
                    expire = diff.toMinutes();;
                }

                sleepTask.awakeMong(mongId, expire);
                healthScheduler.startScheduler(mongId);
                satietyScheduler.startScheduler(mongId);
                poopScheduler.startScheduler(mongId);
                deathScheduler.restartScheduler(mongId);
                evolutionScheduler.restartScheduler(mongId);
                minusScheduler(mongId);
            }catch (NotFoundMongException e){
                log.info("{}의 몽이 없습니다.", mongId);
                stopScheduler(mongId);
            }

        };
    }

    public Trigger getTrigger() {
        return null;
    }

    public Trigger getCronTrigger(LocalTime time){
        return new CronTrigger("0 "+time.getMinute()+" "+time.getHour()+" * * ?");
    }

    public Long getDelay() {
        return 30L * 60L;
    }
}
