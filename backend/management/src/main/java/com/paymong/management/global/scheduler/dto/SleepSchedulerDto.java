package com.paymong.management.global.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SleepSchedulerDto {

    private LocalTime sleepStart;
    private LocalTime sleepEnd;
    private Long expire;
    private ThreadPoolTaskScheduler scheduler;

}
