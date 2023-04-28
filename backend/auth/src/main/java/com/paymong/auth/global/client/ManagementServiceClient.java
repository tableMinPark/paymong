package com.paymong.auth.global.client;

import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name ="MANAGEMENT")
public interface ManagementServiceClient {
    @GetMapping("/management/mong")
    Optional<Long> findMongId(Long memberId);
}
