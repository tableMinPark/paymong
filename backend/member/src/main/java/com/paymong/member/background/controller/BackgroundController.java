package com.paymong.member.background.controller;

import com.paymong.member.background.service.BackgroundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/background")
public class BackgroundController {

    private BackgroundService backgroundService;

    @GetMapping("/map")
    public ResponseEntity<Object> findMap(){
        log.info("findMap - Call");

        return null;
    }
}
