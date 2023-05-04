package com.paymong.member.global.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "auth")
public interface AuthServiceClient {
    /*@PutMapping(value = "/auth/member/paypoint", produces = "application/json")
    ResponseEntity<Object>  modifyPaypoint(@RequestHeader("MemberId") String memberId,
                                           @RequestBody ModifyPaypointReqDto modifyPaypointReqDto
                                           );
    */
}
