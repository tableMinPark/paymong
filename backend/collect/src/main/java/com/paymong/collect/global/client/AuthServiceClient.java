package com.paymong.collect.global.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name ="AUTH")
public interface AuthServiceClient {
    @GetMapping("/auth/detail")
    Long findMemberId();
}
