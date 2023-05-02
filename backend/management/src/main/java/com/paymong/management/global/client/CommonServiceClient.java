package com.paymong.management.global.client;


import com.paymong.management.mong.dto.FindRandomEggDto;
import com.paymong.management.status.dto.FindStatusReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "common")
public interface CommonServiceClient {
    @GetMapping("/common/egg")
    public FindRandomEggDto findRandomEgg() throws RuntimeException;

    @GetMapping("/common/status/detail")
    public ResponseEntity<Object> findStatus(FindStatusReqDto findStatusReqDto);
}
