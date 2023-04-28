package com.paymong.battle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BattleService {

    public void battleReady(String accessToken, Long memberId) {
        log.info(accessToken + " " + memberId);
    }
}
