package com.paymong.member.global.client;

import com.paymong.member.paypoint.dto.ModifyPaypointReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth")
public interface AuthServiceClient {
    @PutMapping(value = "/auth/member/paypoint", produces = "application/json")
    ResponseEntity<Object>  modifyPaypoint(@RequestHeader("MemberId") String memberId,
                                           @RequestBody ModifyPaypointReqDto modifyPaypointReqDto
                                           );

}
