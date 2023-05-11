package com.paymong.management.global.scheduler.task;

import com.paymong.management.global.client.ClientService;
import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.dto.*;
import com.paymong.management.global.exception.GatewayException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.scheduler.dto.NextLevelDto;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class EvolutionTask {
    private final MongRepository mongRepository;

    @Transactional
    public void evolutionMong(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
                .orElseThrow(() -> new NotFoundMongException());
        // 해당 몽 졸업
        log.info("진화 대기 상태로 들어갑니다. id : {}", mongId);
        mong.setStateCode(MongConditionCode.EVOLUTION_READY.getCode());
    }

}

