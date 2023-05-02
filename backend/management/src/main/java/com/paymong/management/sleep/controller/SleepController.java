package com.paymong.management.sleep.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management/sleep")
public class SleepController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SleepController.class);

    @PutMapping("/sleep")
    public ResponseEntity<Object> sleepMong(){
        return null;
    }


}
