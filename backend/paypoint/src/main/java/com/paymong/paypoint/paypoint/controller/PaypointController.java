package com.paymong.paypoint.paypoint.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/paypoint")
public class PaypointController {
    @GetMapping("/")
    public void a(){
        System.out.println("paypoint test!!!");
        System.out.println("aaa");
    }

}
