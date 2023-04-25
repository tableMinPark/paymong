package com.paymong.auth.auth.controller;

import com.paymong.auth.auth.dto.request.LoginRequestDto;
import com.paymong.auth.auth.dto.request.RegisterRequestDto;
import com.paymong.auth.auth.dto.response.LoginResponseDto;
import com.paymong.auth.auth.service.AuthService;
import com.paymong.auth.global.exception.NotFoundException;
import com.paymong.auth.global.exception.UnAuthException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info("login - Call");
        try {
            LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
            return ResponseEntity.ok().body(loginResponseDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body("NotFoundException");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("RuntimeException");
        }
    }

    @PostMapping("/reissue")
    private ResponseEntity<Object> reissue(
        @RequestHeader(value = "RefreshToken", required = false) String refreshToken) {
        log.info("reissue - Call");

        try {
            LoginResponseDto loginResponse = authService.reissue(refreshToken);
            return ResponseEntity.ok().body(loginResponse);
        } catch (UnAuthException e) {
            return ResponseEntity.badRequest().body("UnAuthException");
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body("NotFoundException");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("RuntimeException");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequestDto registerRequestDto) {
        log.info("register - Call");
        try {
            authService.register(registerRequestDto);
            return ResponseEntity.ok().body("success");
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body("NotFoundException");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("RuntimeException");
        }
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test(){
        log.info("securit/test - Call");
        try{
            return ResponseEntity.ok().body("success");
        }catch (NotFoundException e) {
            return ResponseEntity.badRequest().body("NotFoundException");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("RuntimeException");
        }
    }

}
