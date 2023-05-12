package com.paymong.management.global.scheduler;

import com.paymong.management.global.code.WebSocketCode;
import com.paymong.management.global.socket.service.WebSocketService;
import com.paymong.management.mong.dto.MapCodeWsDto;
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
@RequiredArgsConstructor
@Slf4j
public class MapScheduler {
    private static final Map<Long, ThreadPoolTaskScheduler> schedulerMap = new HashMap<>();
    private final WebSocketService webSocketService;
    public void stopScheduler(Long memberId) {
        if(schedulerMap.containsKey(memberId)){
            log.info("{}의 {} scheduler를 중지합니다.", this.getClass().getSimpleName(), memberId);
            schedulerMap.get(memberId).shutdown();
            schedulerMap.remove(memberId);
        }else{
            log.info("{}의 {} scheduler가 없습니다.", this.getClass().getSimpleName(), memberId);
        }
    }

    public void startScheduler(Long memberId) {
        if(schedulerMap.containsKey(memberId)){
            stopScheduler(memberId);
        }
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        scheduler.setThreadNamePrefix("map-change-" + memberId + "-");
        // 스케쥴러 시작
        log.info("new {}를 추가합니다.", this.getClass().getSimpleName());
        scheduler.schedule(getRunnable(memberId), Date.from(Instant.now().plusSeconds(getDelay())));
        schedulerMap.put(memberId, scheduler);
    }

    public Runnable getRunnable(Long memberId) {
        return () -> {
            webSocketService.sendMap(new MapCodeWsDto(memberId, "MP000"), WebSocketCode.MAP);
            stopScheduler(memberId);
        };
    }

    public Trigger getTrigger() {
        return null;
    }


    public Long getDelay() {
        return 60L * 60L;
    }

    public Boolean checkMong(Long mongId){
        if(schedulerMap.containsKey(mongId)) return true;
        else return false;
    }
}
