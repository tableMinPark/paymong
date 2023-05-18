package com.paymong.management.global.scheduler.service;

import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.scheduler.*;
import com.paymong.management.global.scheduler.task.SleepTask;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerService {
    private final SleepScheduler sleepScheduler;
    private final HealthScheduler healthScheduler;
    private final PoopScheduler poopScheduler;
    private final SatietyScheduler satietyScheduler;

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
        sleepScheduler.minusScheduler(mong.getMongId());

    }

}
