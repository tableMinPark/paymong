package com.paymong.auth.auth.controller;

import com.paymong.auth.auth.dto.request.LoginReqDto;
import com.paymong.auth.auth.dto.response.LoginResDto;
import com.paymong.auth.auth.service.AuthService;
import com.paymong.auth.global.code.ErrorStateCode;
import com.paymong.auth.global.exception.NotFoundException;
import com.paymong.auth.global.exception.TimeoutException;
import com.paymong.auth.global.exception.UnAuthException;
import com.paymong.auth.global.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginReqDto loginRequestDto) {
        log.info("login - Call");
        try {
            LoginResDto loginResDto = authService.login(loginRequestDto);
            return ResponseEntity.ok().body(loginResDto);
        } catch (NotFoundException e) {
            // 없으면 회원 등록
            LoginResDto loginResDto = authService.register(loginRequestDto);
            log.info("register - Call");
            return ResponseEntity.badRequest().body(loginResDto);
        } catch (UnAuthException e) {
            log.error(ErrorStateCode.UNAUTHORIXED.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.UNAUTHORIXED));
        } catch (TimeoutException e) {
            log.error(ErrorStateCode.REDIS.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.REDIS));
        } catch (RuntimeException e) {
            log.error(ErrorStateCode.RUNTIME.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        log.info("Authtest - Call");
        try {
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.error(ErrorStateCode.NOTFOUNDUSER.getMessage());
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(ErrorStateCode.NOTFOUNDUSER));
        } catch (RuntimeException e) {
            log.error(ErrorStateCode.RUNTIME.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));

        }
    }

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
