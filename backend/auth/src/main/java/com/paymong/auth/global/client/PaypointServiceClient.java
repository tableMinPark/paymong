package com.paymong.auth.global.client;

import com.paymong.auth.member.dto.request.AddPointReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "paypoint")
public interface PaypointServiceClient {

    @PostMapping("/paypoint/point")
    ResponseEntity<Object> addPoint(@RequestHeader("MemberId") String memberId,
        @RequestBody AddPointReqDto addPointReqDto);
}
