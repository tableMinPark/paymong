package com.paymong.management.global.config;

import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.redis.RedisService;
import com.paymong.management.global.scheduler.DeathScheduler;
import com.paymong.management.global.scheduler.EvolutionScheduler;
import com.paymong.management.global.scheduler.SleepScheduler;
import com.paymong.management.global.scheduler.service.SchedulerService;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartConfig implements CommandLineRunner {
    private final RedisService redisService;
    private final DeathScheduler deathScheduler;
    private final EvolutionScheduler evolutionScheduler;
    private final SleepScheduler sleepScheduler;
    private final SchedulerService schedulerService;
    private final MongRepository mongRepository;
    @Override
    public void run(String... args) throws Exception {
        log.info("시자~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~악 하겠습니다.");
        log.info("death 스케줄러를 넣습니다.");
        redisService.getRedisMong("death").stream().forEach(deathScheduler::restartScheduler);
        log.info("evolution 스케줄러를 넣습니다.");
        redisService.getRedisMong("evolution").stream().forEach(evolutionScheduler::restartScheduler);
        log.info("sleep 스케줄러를 넣습니다.");
        redisService.getRedisMong("sleep").stream().forEach(sleepScheduler::resleepScheduler);
        log.info("나머지 스케줄러를 가동합니다.");

        List<Mong> mongs = mongRepository.findByActive(true);

//        List<Mong> mongs = new ArrayList<>();
//        mongs.add(mongRepository.findByMongId(66L).get());
//        mongs.add(mongRepository.findByMongId(67L).get());
        mongs.stream()
                .filter(mong -> !mong.getStateCode().equals(MongConditionCode.SLEEP.getCode())
                        && !mong.getStateCode().equals(MongConditionCode.DIE.getCode())
                        && !mong.getStateCode().equals(MongConditionCode.GRADUATE.getCode()))
                        .forEach(schedulerService::startScheduler);

    }
}
