package com.paymong.auth.global.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name ="INFORMATION")
public interface InformationServiceClient {
    @GetMapping("/information/??")
    Long findMongId();
}
