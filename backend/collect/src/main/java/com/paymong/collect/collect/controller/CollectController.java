package com.paymong.collect.collect.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/collect")
public class CollectController {
    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        log.info("collect/test - Call");
        try {
            return ResponseEntity.ok().body("success");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("fail");
        }
    }
}
