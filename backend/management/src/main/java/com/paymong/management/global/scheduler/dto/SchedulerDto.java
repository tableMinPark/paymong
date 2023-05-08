package com.paymong.management.global.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchedulerDto {
    private LocalDateTime startTime;
    // 초단위
    private Long expire;
    private ThreadPoolTaskScheduler scheduler;

    public void initScheduler(String message, Runnable runnable){
        this.scheduler = new ThreadPoolTaskScheduler();
        this.scheduler.initialize();
        this.scheduler.setThreadNamePrefix(message);
        this.scheduler.schedule(runnable, Instant.now().plusSeconds(this.expire));
    }
}
