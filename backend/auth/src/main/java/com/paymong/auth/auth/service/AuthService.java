package com.paymong.auth.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.auth.auth.dto.request.LoginReqDto;
import com.paymong.auth.auth.dto.response.LoginResDto;
import com.paymong.auth.auth.dto.response.MemberLoginResDto;
import com.paymong.auth.auth.dto.response.ReissueResDto;
import com.paymong.auth.auth.entity.Auth;
import com.paymong.auth.auth.repository.AuthRepository;
import com.paymong.auth.global.client.MemberServiceClient;
import com.paymong.auth.global.code.JwtStateCode;
import com.paymong.auth.global.exception.ForbiddenException;
import com.paymong.auth.global.exception.NotFoundException;
import com.paymong.auth.global.exception.TimeoutException;
import com.paymong.auth.global.exception.TokenInvalidException;
import com.paymong.auth.global.exception.TokenUnauthException;
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
import org.springframework.util.StringUtils;

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

        // 토큰 발급 및 캐시 서버에 저장
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

        // 캐시 서버에 token 저장
        try {
            refreshTokenRedisRepository.save(RefreshToken.builder()
                .id(loginReqDto.getPlayerId())
                .memberKey(String.valueOf(memberLoginResDto.getMemberId()))
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .expiration(JwtStateCode.REFRESH_TOKEN_EXPIRATION_PERIOD.getValue()).build());
        } catch (Exception e) {
            //redis 에러 처리
            log.info(e.getMessage());
            throw new TimeoutException();
        }

        return TokenInfo.builder().acessToken(accessToken).refreshToken(refreshToken).build();
    }

    @Transactional
    public ReissueResDto reissue(String token) throws RuntimeException {

        // refresh 토큰이 없거나 jwt 형식이 아닌 경우
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new TokenInvalidException();
        }

        String userName = tokenProvider.getUsername(token);
        String newAccessToken = tokenProvider.generateAccessToken(userName);

        // 캐시 서버에서 토큰을 찾을 수 없을 때 ( refreshToken 만료 )
        // 사용자가 로그인을 다시 해야함
        RefreshToken refreshToken = refreshTokenRedisRepository.findById(userName)
            .orElseThrow(() -> new ForbiddenException());

        // 리프레쉬 토큰 불일치
        if(!refreshToken.getRefreshToken().equals(token))throw new TokenUnauthException();


        RefreshToken newRefreshToken =
            RefreshToken.builder()
                .id(refreshToken.getId())
                .memberKey(refreshToken.getMemberKey())
                .refreshToken(refreshToken.getRefreshToken())
                .accessToken(newAccessToken)
                .expiration(JwtStateCode.REFRESH_TOKEN_EXPIRATION_PERIOD.getValue())
                .build();

        // 캐시 서버에 token 저장
        try {
            refreshTokenRedisRepository.save(newRefreshToken);
        } catch (Exception e) {
            //redis 에러 처리
            log.info(e.getMessage());
            throw new TimeoutException();
        }
        return ReissueResDto.builder()
            .accessToken(newAccessToken)
            .build();
    }


}
