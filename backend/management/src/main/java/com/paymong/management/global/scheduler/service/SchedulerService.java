package com.paymong.management.global.scheduler.service;

import com.paymong.management.mong.scheduler.DeathScheduler;
import com.paymong.management.global.scheduler.HealthScheduler;
import com.paymong.management.poop.scheduler.PoopScheduler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);
    private final PoopScheduler poopScheduler;
    private final HealthScheduler healthScheduler;
    private final DeathScheduler deathScheduler;

    /*
    0 : poop
    1 : health
    2 : satiety
    3 : death
    4 : evolution
    5 : sleep
    6 : all
     */

    public void startOf(Integer sc, Long mongId){

        if(sc == 0){
            LOGGER.info("start poop scheduler filter");
            poopScheduler.startScheduler(mongId);
        }else if(sc == 1){
            LOGGER.info("start health scheduler filter");
            healthScheduler.startScheduler(mongId);
        }else if(sc == 3){
            LOGGER.info("start death scheduler filter");
            deathScheduler.startScheduler(mongId);
        }
    }

    public void stopOf(Integer sc, Long mongId){
        LOGGER.info("stop {} scheduler filter", sc);
        if(sc == 0){
            poopScheduler.stopScheduler(mongId);
        }else if(sc == 1){
            healthScheduler.stopScheduler(mongId);
        }else if(sc == 3){
            deathScheduler.stopScheduler(mongId);
        }else if(sc == 6){
            poopScheduler.stopScheduler(mongId);
            healthScheduler.stopScheduler(mongId);
            deathScheduler.stopScheduler(mongId);
        }
    }

}
