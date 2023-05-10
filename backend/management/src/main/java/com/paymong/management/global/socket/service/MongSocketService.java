package com.paymong.management.global.socket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class MongSocketService {
    private final ObjectMapper objectMapper;

    @PostConstruct
    private void init(){

    }

}
