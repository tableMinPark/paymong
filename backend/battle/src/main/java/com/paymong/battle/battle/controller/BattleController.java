package com.paymong.battle.battle.controller;

import com.paymong.battle.battle.service.BattleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/battle")
public class BattleController {

    private final BattleService battleService;

    @GetMapping("/test")
    public ResponseEntity<Object> test() {

        return ResponseEntity.ok().body(battleService.findCharacterStats(66L, 500.0));
    }
}