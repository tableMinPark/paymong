package com.paymong.auth.member.controller;

import com.paymong.auth.auth.dto.request.RegisterReqDto;
import com.paymong.auth.global.code.ErrorStateCode;
import com.paymong.auth.global.exception.NotFoundException;
import com.paymong.auth.global.response.ErrorResponse;
import com.paymong.auth.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/member")
public class MemberController {

    private final MemberService memberService;


    //    @GetMapping("/info")
//    public ResponseEntity<Object> findMemberInfo() {
//        log.info("findMember - Call");
//        try {
//
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
//        }
//    }

    //    @PostMapping("/reissue")
//    private ResponseEntity<Object> reissue(
//        @RequestHeader(value = "RefreshToken", required = false) String refreshToken) {
//        log.info("reissue - Call");
//
//        try {
//            LoginResDto loginResponse = authService.reissue(refreshToken);
//            return ResponseEntity.ok().body(loginResponse);
//        } catch (UnAuthException e) {
//            return ResponseEntity.badRequest()
//                .body(new ErrorResponse(ErrorStateCode.UNAUTHORIXED));
//        } catch (NotFoundException e) {
//            return ResponseEntity.badRequest()
//                .body(new ErrorResponse(ErrorStateCode.NOTFOUNDUSER));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
//        }
//    }
}
