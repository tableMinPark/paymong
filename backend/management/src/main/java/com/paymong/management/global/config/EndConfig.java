package com.paymong.management.global.config;

import com.paymong.management.global.scheduler.DeathScheduler;
import com.paymong.management.global.scheduler.EvolutionScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EndConfig implements ApplicationListener<ContextClosedEvent> {
    private final DeathScheduler deathScheduler;
    private final EvolutionScheduler evolutionScheduler;
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("death 스케줄러 내용을 넣습니다.");
        deathScheduler.addRedis();
        log.info("evolution 스케줄러 내용을 넣습니다.");
        evolutionScheduler.addRedis();
        log.info("정상적으로 종료되나봅시다.");
    }
}
