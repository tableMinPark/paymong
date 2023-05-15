package com.paymong.information.global.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "member")
public interface MemberServiceClient {

    @GetMapping("/member/background/mymap")
    ResponseEntity<Object> findMymap(@RequestHeader(value = "MemberId") String memberIdStr);
}
