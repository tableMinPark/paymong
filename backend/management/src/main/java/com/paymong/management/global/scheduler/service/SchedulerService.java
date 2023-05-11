package com.paymong.management.global.scheduler.service;

import com.paymong.management.global.scheduler.*;
import com.paymong.management.global.scheduler.task.SleepTask;
import com.paymong.management.mong.entity.Mong;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);
    private final SleepScheduler sleepScheduler;
    private final HealthScheduler healthScheduler;
    private final PoopScheduler poopScheduler;
    private final SatietyScheduler satietyScheduler;
    private final EvolutionScheduler evolutionScheduler;
    /*
    0 : poop
    1 : health
    2 : satiety
    3 : death
    4 : evolution
    5 : sleep
    6 : all
     */

    public void startScheduler(Mong mong){


        healthScheduler.startScheduler(mong.getMongId());
        poopScheduler.startScheduler(mong.getMongId());
        satietyScheduler.startScheduler(mong.getMongId());

        evolutionScheduler.startScheduler(mong.getMongId());
        sleepScheduler.initScheduler(mong.getMongId(), mong.getSleepStart(), mong.getSleepEnd());
    }


}
