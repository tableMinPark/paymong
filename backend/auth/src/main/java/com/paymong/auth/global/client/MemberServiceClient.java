package com.paymong.auth.global.client;

import com.paymong.auth.auth.dto.request.FindByPlayIdReqDto;
import com.paymong.auth.auth.dto.request.LoginReqDto;
import com.paymong.auth.member.dto.request.AddPointReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "member")
public interface MemberServiceClient {

    @PostMapping("/member/login")
    ResponseEntity<Object> memberLogin(FindByPlayIdReqDto findByPlayIdReqDto);

    @PostMapping("/member/register")
    ResponseEntity<Object> memberRegister(FindByPlayIdReqDto findByPlayIdReqDto);

}
