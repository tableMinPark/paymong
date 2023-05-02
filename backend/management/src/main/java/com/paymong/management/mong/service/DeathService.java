package com.paymong.management.mong.service;

import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.scheduler.service.SchedulerService;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.mong.scheduler.HealthScheduler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DeathService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeathService.class);
    private final MongRepository mongRepository;

    @Transactional
    public void deathMong(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findByMongId(mongId)
                .orElseThrow(() -> new NotFoundMongException());
        mong.setActive(false);
        LOGGER.info("{} 의 mong이 죽었습니다.", mongId);
    }
}
