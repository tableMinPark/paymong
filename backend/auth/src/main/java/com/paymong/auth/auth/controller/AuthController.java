package com.paymong.auth.auth.controller;

import com.paymong.auth.auth.dto.request.LoginReqDto;
import com.paymong.auth.auth.dto.response.LoginResDto;
import com.paymong.auth.auth.dto.response.ReissueResDto;
import com.paymong.auth.auth.service.AuthService;
import com.paymong.auth.global.code.ErrorStateCode;
import com.paymong.auth.global.exception.ForbiddenException;
import com.paymong.auth.global.exception.NotFoundException;
import com.paymong.auth.global.exception.TimeoutException;
import com.paymong.auth.global.exception.TokenInvalidException;
import com.paymong.auth.global.exception.TokenUnauthException;
import com.paymong.auth.global.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> login(@RequestBody LoginReqDto loginRequestDto) {
        log.info("login - Call");
        try {
            LoginResDto loginResDto = authService.login(loginRequestDto);
            log.info("code : {}, message : {}", ErrorStateCode.SUCCESS.getCode(),
                ErrorStateCode.SUCCESS.name());
            return ResponseEntity.ok().body(loginResDto);
        } catch (NotFoundException e) {
            log.info("code : {}, message : {}", ErrorStateCode.GATEWAY.getCode(),
                ErrorStateCode.GATEWAY.name());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.GATEWAY));
        } catch (TimeoutException e){
            log.info("code : {}, message : {}", ErrorStateCode.REDIS.getCode(),
                ErrorStateCode.REDIS.name());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.REDIS));
        } catch (RuntimeException e) {
            log.info("code : {}, message : {}", ErrorStateCode.RUNTIME.getCode(),
                ErrorStateCode.RUNTIME.name());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }

//    @GetMapping("/test")
//    public ResponseEntity<Object> test() {
//        log.info("Authtest - Call");
//        try {
//            log.info("code : {}, message : {}", ErrorStateCode.SUCCESS.getCode(),
//                ErrorStateCode.SUCCESS.name());
//            return ResponseEntity.ok().build();
//        } catch (NotFoundException e) {
//            log.info("code : {}, message : {}", ErrorStateCode.NOTFOUNDUSER.getCode(),
//                ErrorStateCode.NOTFOUNDUSER.name());
//            return ResponseEntity.badRequest()
//                .body(new ErrorResponse(ErrorStateCode.NOTFOUNDUSER));
//        } catch (RuntimeException e) {
//            log.info("code : {}, message : {}", ErrorStateCode.RUNTIME.getCode(),
//                ErrorStateCode.RUNTIME.name());
//            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
//
//        }
//    }

    @PostMapping("/reissue")
    private ResponseEntity<Object> reissue(
        @RequestHeader(value = "RefreshToken", required = false) String refreshToken) {
        log.info("reissue - Call");

        try {
            log.info("code : {}, message : {}", ErrorStateCode.SUCCESS.getCode(),
                ErrorStateCode.SUCCESS.name());
            ReissueResDto reissueResDto = authService.reissue(refreshToken);
            return ResponseEntity.ok().body(reissueResDto);
        } catch (ForbiddenException e) {
            log.info("code : {}, message : {}", ErrorStateCode.REFRESH_TOKEN_EXPIRE.getCode(),
                ErrorStateCode.REFRESH_TOKEN_EXPIRE.name());
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ErrorStateCode.REFRESH_TOKEN_EXPIRE));
        }catch (TokenInvalidException e){
            log.info("code : {}, message : {}", ErrorStateCode.TOKEN_INVALID.getCode(),ErrorStateCode.TOKEN_INVALID.getMessage());
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(ErrorStateCode.TOKEN_INVALID));
        }catch (TokenUnauthException e){
            log.info("code : {}, message : {}", ErrorStateCode.TOKEN_UNAUTH.getCode(),ErrorStateCode.TOKEN_UNAUTH.getMessage());
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(ErrorStateCode.TOKEN_UNAUTH));
        }catch (TimeoutException e) {
            log.info("code : {}, message : {}", ErrorStateCode.REDIS.getCode(),
                ErrorStateCode.REDIS.name());
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(ErrorStateCode.REDIS));
        } catch (RuntimeException e) {
            log.info("code : {}, message : {}", ErrorStateCode.RUNTIME.getCode(),
                ErrorStateCode.RUNTIME.name());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }

}
