package com.paymong.management.poop.scheduler;

import com.paymong.management.global.scheduler.ManagementScheduler;
import com.paymong.management.poop.service.PoopService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class PoopScheduler extends ManagementScheduler {

    private final PoopService poopService;

    @Override
    public Runnable getRunnable(Long mongId) {
        System.out.println("mong : " + mongId);
        return () -> {
          poopService.testPoop(mongId);
        };
    }

    @Override
    public Trigger getTrigger() {
        return new PeriodicTrigger(5, TimeUnit.SECONDS);
    }

}
