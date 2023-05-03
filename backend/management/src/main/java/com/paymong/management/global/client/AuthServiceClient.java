package com.paymong.management.global.client;

import com.paymong.management.global.dto.AddPointDto;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("auth")
public interface AuthServiceClient {
    @PutMapping(value = "/auth/member/management/paypoint")
    public ResponseEntity<Object> addPoint(@RequestHeader("MemberId") String memberId, @RequestBody AddPointDto addPayDto);
}
