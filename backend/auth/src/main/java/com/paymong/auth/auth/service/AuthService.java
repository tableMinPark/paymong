package com.paymong.auth.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.auth.auth.dto.request.LoginReqDto;
import com.paymong.auth.auth.dto.response.LoginResDto;
import com.paymong.auth.auth.dto.response.MemberLoginResDto;
import com.paymong.auth.auth.entity.Auth;
import com.paymong.auth.auth.repository.AuthRepository;
import com.paymong.auth.global.client.MemberServiceClient;
import com.paymong.auth.global.code.JwtStateCode;
import com.paymong.auth.global.exception.NotFoundException;
import com.paymong.auth.global.exception.TimeoutException;
import com.paymong.auth.global.redis.RefreshToken;
import com.paymong.auth.global.redis.RefreshTokenRedisRepository;
import com.paymong.auth.global.security.TokenInfo;
import com.paymong.auth.global.security.TokenProvider;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    private final TokenProvider tokenProvider;

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    private final PasswordEncoder passwordEncoder;

    private final MemberServiceClient memberServiceClient;

    @Transactional
    public LoginResDto login(LoginReqDto loginReqDto) throws RuntimeException {
        String password = passwordEncoder.encode(UUID.randomUUID().toString());

        loginReqDto.setPassword(password);
        //try-catch 잡기
        MemberLoginResDto memberLoginResDto;
        try {
            // loginReq to findByPlayIdReqDto 만들기
            ObjectMapper om = new ObjectMapper();
            memberLoginResDto = om.convertValue(
                memberServiceClient.memberLogin(loginReqDto).getBody(), MemberLoginResDto.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new NotFoundException();
        }

        // 토큰 발급
        TokenInfo tokenInfo = provideToken(loginReqDto, memberLoginResDto);

        // 역할 설정
        authRepository.save(
            Auth.builder().memberId(String.valueOf(memberLoginResDto.getMemberId())).role("USER")
                .build());

        return LoginResDto.builder()
            .accessToken(tokenInfo.getAcessToken())
            .refreshToken(tokenInfo.getRefreshToken())
            .build();

    }

    public TokenInfo provideToken(LoginReqDto loginReqDto, MemberLoginResDto memberLoginResDto)
        throws RuntimeException {
        String accessToken = tokenProvider.generateAccessToken(loginReqDto.getPlayerId());
        String refreshToken = tokenProvider.generateRefreshToken(loginReqDto.getPlayerId());

        try {
            refreshTokenRedisRepository.save(RefreshToken.builder()
                .id(loginReqDto.getPlayerId())
                .memberKey(String.valueOf(memberLoginResDto.getMemberId()))
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .expiration(JwtStateCode.ACCESS_TOKEN_EXPIRATION_PERIOD.getValue()).build());
        } catch (Exception e) {
            //redis 에러 처리
            log.info(e.getMessage());
            throw new TimeoutException();
        }

        return TokenInfo.builder().acessToken(accessToken).refreshToken(refreshToken).build();
    }

//    @Transactional
//    public LoginResDto register(LoginReqDto loginReqDto) throws RuntimeException {
//        String password = passwordEncoder.encode(UUID.randomUUID().toString());
//        MemberRegisterReqDto memberRegisterReqDto = new MemberRegisterReqDto();
//        //loginReqDto to memberRegisterReqDto 만들기
//
////        memberRepository.save(member);
//        Auth auth = Auth.builder().build();
//        authRepository.save(auth);
//        FindByPlayIdResDto findByPlayIdResDto;
//        try {
//            ObjectMapper om = new ObjectMapper();
//            findByPlayIdResDto = om.convertValue(
//                memberServiceClient.memberRegister(memberRegisterReqDto), FindByPlayIdResDto.class);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new NotFoundException();
//        }
//
//        TokenInfo tokenInfo = provideToken(findByPlayIdResDto);
//
//        return LoginResDto.builder()
//            .accessToken(tokenInfo.getAcessToken())
//            .refreshToken(tokenInfo.getRefreshToken())
//            .build();
//    }
//

}
