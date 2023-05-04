package com.paymong.auth.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.auth.auth.dto.request.FindByPlayIdReqDto;
import com.paymong.auth.auth.dto.request.LoginReqDto;
import com.paymong.auth.auth.dto.response.FindByPlayIdResDto;
import com.paymong.auth.auth.dto.response.LoginResDto;
import com.paymong.auth.auth.entity.Auth;
import com.paymong.auth.auth.entity.Member;
import com.paymong.auth.auth.repository.AuthRepository;
import com.paymong.auth.auth.repository.MemberRepository;
import com.paymong.auth.global.client.MemberServiceClient;
import com.paymong.auth.global.code.JwtStateCode;
import com.paymong.auth.global.exception.NotFoundException;
import com.paymong.auth.global.exception.TimeoutException;
import com.paymong.auth.global.exception.UnAuthException;
import com.paymong.auth.global.redis.RefreshToken;
import com.paymong.auth.global.redis.RefreshTokenRedisRepository;
import com.paymong.auth.global.security.TokenInfo;
import com.paymong.auth.global.security.TokenProvider;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    private final AuthRepository authRepository;

    private final TokenProvider tokenProvider;

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    private final PasswordEncoder passwordEncoder;

    private final MemberServiceClient memberServiceClient;

    @Transactional
    public LoginResDto login(LoginReqDto loginReqDto) throws RuntimeException {

        //try-catch 잡기
        try{
            // loginReq to findByPlayIdReqDto 만들기
            FindByPlayIdReqDto findByPlayIdReqDto;
            ObjectMapper om = new ObjectMapper();
            FindByPlayIdResDto findByPlayIdResDto = om.convertValue(memberServiceClient.findByPlayerId(findByPlayIdReqDto).getBody()) ;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new NotFoundException();
        }

//        findByPlayIdResDto to Member 만들기

        // 토큰 발급
        TokenInfo tokenInfo = provideToken(findByPlayIdResDto);

        // 역할 설정
        findByPlayIdResDto
        authRepository.findByMember(member).orElseThrow(() -> new UnAuthException());

        return LoginResDto.builder()
            .accessToken(tokenInfo.getAcessToken())
            .refreshToken(tokenInfo.getRefreshToken())
            .build();

    }

    public TokenInfo provideToken(FindByPlayIdResDto member) throws RuntimeException {
        String accessToken = tokenProvider.generateAccessToken(member.getPlayerId());
        String refreshToken = tokenProvider.generateRefreshToken(member.getPlayerId());

        try {
            refreshTokenRedisRepository.save(RefreshToken.builder()
                .id(member.getPlayerId())
                .memberKey(String.valueOf(member.getMemberId()))
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

    @Transactional
    public LoginResDto register(LoginReqDto loginReqDto) throws RuntimeException{
        String password = passwordEncoder.encode(UUID.randomUUID().toString());
        Member member = Member.builder().playerId(loginReqDto.getPlayerId()).password(password)
            .build();
        memberServiceClient.memberRegister();
        FindByPlayIdReqDto findByPlayIdReqDto
        memberServiceClient.memberRegister()
        memberRepository.save(member);
        Auth auth = Auth.of("USER", member);
        authRepository.save(auth);
        TokenInfo tokenInfo = provideToken(member);
        return LoginResDto.builder()
            .accessToken(tokenInfo.getAcessToken())
            .refreshToken(tokenInfo.getRefreshToken())
            .build();
    }


}
