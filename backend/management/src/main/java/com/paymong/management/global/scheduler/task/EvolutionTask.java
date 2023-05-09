package com.paymong.management.global.scheduler.task;

import com.paymong.management.mong.repository.MongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EvolutionTask {
    private final MongRepository mongRepository;

    public Integer evolutionMong2Level1(Long mongId){
        return 0;
    }
}
