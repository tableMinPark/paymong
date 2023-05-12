package com.paymong.management.global.scheduler;

import com.paymong.management.global.dto.FindMongLevelCodeDto;
import com.paymong.management.global.exception.GatewayException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.redis.RedisMong;
import com.paymong.management.global.redis.RedisService;
import com.paymong.management.global.scheduler.dto.NextLevelDto;
import com.paymong.management.global.scheduler.dto.SchedulerDto;
import com.paymong.management.global.scheduler.task.EvolutionTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class EvolutionScheduler implements ManagementScheduler {

    private static final Map<Long, SchedulerDto> schedulerMap = new HashMap<>();
    private final EvolutionTask evolutionTask;
    private final RedisService redisService;
    @Override
    public void stopScheduler(Long mongId) {
        if(schedulerMap.containsKey(mongId)){
            log.info("{}의 {} evolution scheduler를 중지합니다.", this.getClass().getSimpleName(), mongId);
            schedulerMap.get(mongId).getScheduler().shutdown();
            schedulerMap.remove(mongId);
        }else{
            log.info("{}의 {} evolution scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }
    }

    public void pauseScheduler(Long mongId) {
        if(schedulerMap.containsKey(mongId)){
            SchedulerDto schedulerDto = schedulerMap.get(mongId);
            Duration diff = Duration.between(schedulerDto.getStartTime(), LocalDateTime.now());
            Long expire = schedulerDto.getExpire();
            if(expire - diff.toSeconds() < 0){
                try {
                    evolutionTask.evolutionMong(mongId);
                }catch (NotFoundMongException e){
                    stopScheduler(mongId);
                }
            }else{
                // 남은 기간 계산
                log.info("남은 기간 : {}", expire - diff.toSeconds());
                schedulerDto.setExpire(expire - diff.toSeconds());
                // 대기중인 스케줄러 정지
                schedulerDto.getScheduler().shutdown();
            }

        }else{
            log.info("{}의 {} evolution scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }
    }

    @Override
    public void startScheduler(Long mongId) {
        if(schedulerMap.containsKey(mongId)){
            log.info("이미 진화 중에 있습니다. {}", mongId);
            return;
        }
        SchedulerDto schedulerDto = new SchedulerDto();
        schedulerDto.setStartTime(LocalDateTime.now());
        schedulerDto.setExpire(60L * 10L);
        schedulerDto.setMessage("Evolution-to-level1-");
        schedulerDto.setRunnable(getRunnable(mongId));
        schedulerDto.initScheduler();

        log.info("{}의 evolution 스케쥴러를 시작합니다. 남은 기간 : {}", this.getClass().getSimpleName(), schedulerDto.getExpire());

        schedulerMap.put(mongId, schedulerDto);
    }

    public void nextLevelScheduler(NextLevelDto levelDto){
        if(!schedulerMap.containsKey(levelDto.getMongId())){
            log.info("{}는 없는 아이입니다. 새롭게 추가합니다.", levelDto.getMongId());
            SchedulerDto scDto= new SchedulerDto();
            scDto.setRunnable(getRunnable(levelDto.getMongId()));
            schedulerMap.put(levelDto.getMongId(), scDto);
        }else{
            log.info("{}의 존재중인 스케줄러를 중지합니다.", levelDto.getMongId());
            schedulerMap.get(levelDto.getMongId()).getScheduler().shutdown();
        }

        SchedulerDto schedulerDto = schedulerMap.get(levelDto.getMongId());
        if(levelDto.getLevel() == 1){
            schedulerDto.setExpire(60L*60L*12L);
            schedulerDto.setMessage("Evolution-to-level2-");
        }else if(levelDto.getLevel() == 2){
            schedulerDto.setExpire(60L*60L*36L);
            schedulerDto.setMessage("Evolution-to-level3-");
        }else if(levelDto.getLevel() == 3){
            schedulerDto.setExpire(60L*60L*24L);
            schedulerDto.setMessage("Evolution-to-graduation-");
        }else{
            log.info("레벨이 맞지 않습니다.");
            return;
        }
        schedulerDto.setStartTime(LocalDateTime.now());
        schedulerDto.initScheduler();

        log.info("{}의 evolution 스케쥴러를 시작합니다. 남은 기간 : {}", this.getClass().getSimpleName(), schedulerDto.getExpire());
        schedulerMap.put(levelDto.getMongId(), schedulerDto);

    }

    public void restartScheduler(Long mongId){
        if(schedulerMap.containsKey(mongId)){
            SchedulerDto schedulerDto = schedulerMap.get(mongId);
            schedulerDto.setStartTime(LocalDateTime.now());
            schedulerDto.initScheduler();
            log.info("{}의 evolution 스케쥴러를 재시작합니다. 남은 기간 : {}", this.getClass().getSimpleName(), schedulerDto.getExpire());

        }else{
            log.info("{}의 {} evolution scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }
    }

    public void restartScheduler(RedisMong redisMong){
        if(schedulerMap.containsKey(redisMong.getMongId())){
            log.info("{}의 {} evolution scheduler가 이미 돌아가고 있습니다.", this.getClass().getSimpleName(), redisMong.getMongId());

        }else {
            SchedulerDto schedulerDto = new SchedulerDto();
            schedulerDto.setStartTime(LocalDateTime.now());
            schedulerDto.setExpire(redisMong.getExpire());
            schedulerDto.setMessage("evolution-");
            schedulerDto.setRunnable(getRunnable(redisMong.getMongId()));
            schedulerDto.initScheduler();
            log.info("{}의 evolution 스케쥴러를 재시작합니다. 남은 기간 : {}", this.getClass().getSimpleName(), schedulerDto.getExpire());

            schedulerMap.put(redisMong.getMongId(), schedulerDto);
        }
    }

    public void addRedis(){
        redisService.addRedis(schedulerMap, "evolution");
    }

    @Override
    public Runnable getRunnable(Long mongId) {

        return () -> {
            try {
                evolutionTask.evolutionMong(mongId);
                stopScheduler(mongId);
            }catch (NotFoundMongException e){
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
        return null;
    }
}
