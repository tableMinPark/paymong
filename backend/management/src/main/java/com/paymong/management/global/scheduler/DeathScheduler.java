package com.paymong.management.global.scheduler;

import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.scheduler.dto.SchedulerDto;
import com.paymong.management.global.scheduler.task.DeathTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeathScheduler implements ManagementScheduler{
    private static final Map<Long, SchedulerDto> schedulerMap = new HashMap<>();
    private final DeathTask deathTask;
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

    public void pauseScheduler(Long mongId) {
        if(schedulerMap.containsKey(mongId)){
            SchedulerDto schedulerDto = schedulerMap.get(mongId);
            Duration diff = Duration.between(schedulerDto.getStartTime(), LocalDateTime.now());
            Long expire = schedulerDto.getExpire();
            if(expire - diff.toSeconds() < 0){
                try {
                    deathTask.deathMong(mongId);
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
            log.info("{}의 {} death scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }
    }

    @Override
    public void startScheduler(Long mongId) {
        if(schedulerMap.containsKey(mongId)){
            log.info("이미 죽어가고 있습니다. {}", mongId);
            return;
        }
        SchedulerDto schedulerDto = new SchedulerDto();
        schedulerDto.setStartTime(LocalDateTime.now());
        schedulerDto.setExpire(60L * 60L * 3L);
        schedulerDto.setMessage("health-Death-");
        schedulerDto.setRunnable(getRunnable(mongId));
        schedulerDto.initScheduler();

        log.info("건강악화로 {}의 죽음 스케쥴러를 시작합니다. 남은 기간 : {}", this.getClass().getSimpleName(), schedulerDto.getExpire());

        schedulerMap.put(mongId, schedulerDto);
    }

    public void restartScheduler(Long mongId){
        if(schedulerMap.containsKey(mongId)){
            SchedulerDto schedulerDto = schedulerMap.get(mongId);
            schedulerDto.setStartTime(LocalDateTime.now());
            schedulerDto.initScheduler();
            log.info("건강악화로 {}의 죽음 스케쥴러를 재시작합니다. 남은 기간 : {}", this.getClass().getSimpleName(), schedulerDto.getExpire());

        }else{
            log.info("{}의 {} death scheduler가 없습니다.", this.getClass().getSimpleName(), mongId);
        }
    }

    @Override
    public Runnable getRunnable(Long mongId) {
        return () -> {
            try {
                deathTask.deathMong(mongId);
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
