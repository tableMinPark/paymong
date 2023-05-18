package com.paymong.auth.global.client;

import com.paymong.auth.auth.dto.request.LoginReqDto;
import com.paymong.auth.auth.dto.request.MemberRegisterReqDto;
import com.paymong.auth.auth.dto.request.SecureReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "member")
public interface MemberServiceClient {

    @PostMapping("/member/login")
    ResponseEntity<Object> memberLogin(LoginReqDto loginReqDto);

    @PostMapping("/member/secure")
    ResponseEntity<Object> secure(SecureReqDto secureReqDto);

}
