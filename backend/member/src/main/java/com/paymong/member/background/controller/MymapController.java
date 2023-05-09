package com.paymong.member.background.controller;

import com.paymong.member.background.service.MymapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/background")
public class MymapController {

    private MymapService backgroundService;

    @GetMapping("/mymap")
    public ResponseEntity<Object> findMymap(){
        log.info("findMymap - Call");
        LocalDateTime cur = LocalDateTime.now();
        System.out.println(cur);

        return null;
    }
}
