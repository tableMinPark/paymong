package com.paymong.auth.auth.service;

import com.paymong.auth.auth.dto.request.LoginReqDto;
import com.paymong.auth.auth.dto.request.RegisterReqDto;
import com.paymong.auth.auth.dto.response.FindMemberIdResDto;
import com.paymong.auth.auth.dto.response.LoginResDto;
import com.paymong.auth.auth.entity.Auth;
import com.paymong.auth.auth.entity.Member;
import com.paymong.auth.auth.repository.AuthRepository;
import com.paymong.auth.auth.repository.MemberRepository;
import com.paymong.auth.global.client.ManagementServiceClient;
import com.paymong.auth.global.code.JwtStateCode;
import com.paymong.auth.global.exception.NotFoundException;
import com.paymong.auth.global.redis.RefreshToken;
import com.paymong.auth.global.redis.RefreshTokenRedisRepository;
import com.paymong.auth.global.security.TokenInfo;
import com.paymong.auth.global.security.TokenProvider;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
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




//    @Transactional
//    public void login(RegisterReqDto registerReqDto) {
//        Member member = registerReqDto.toMember();
//        member.setPassword(passwordEncoder.encode(member.getPassword()));
//        memberRepository.save(member);
//        Auth auth = Auth.of("USER", member);
//        authRepository.save(auth);
//    }

    @Transactional
    public LoginResDto login(LoginReqDto loginReqDto) throws RuntimeException {

        Member member = memberRepository.findByPlayerId(loginReqDto.getPlayerId()).orElseThrow(
            () -> new NotFoundException()
        );

        if (member.getMemberId() == null) {

        }
        // 토큰 발급
        TokenInfo tokenInfo = provideToken(member);

        // 역할 설정
        Auth auth = authRepository.findByMember(member).orElseThrow(() -> new NotFoundException());

        return LoginResDto.builder()
            .accessToken(tokenInfo.getAcessToken())
            .refreshToken(tokenInfo.getRefreshToken())
            .build();

    }

    public TokenInfo provideToken(Member member) throws RuntimeException {
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
        }

        return TokenInfo.builder().acessToken(accessToken).refreshToken(refreshToken).build();
    }





    @Transactional
    public Member findByMemberPlayerId(String playerId) {
        return memberRepository.findByPlayerId(playerId).orElseThrow();
    }


//    public FindMemberIdResDto findMemberId() throws RuntimeException {
//        String playerId = SecurityContextHolder.getContext().getAuthentication().getName();
//        Member member = memberRepository.findByPlayerId(playerId)
//            .orElseThrow(() -> new NotFoundException());
//        return new FindMemberIdResDto(member.getMemberId());
//    }

//    @Transactional
//    public LoginResDto reissue(String refreshToken) throws RuntimeException {
//        refreshToken = refreshToken.substring(7);
//
//        String email = tokenProvider.getUsername(refreshToken);
//
//        RefreshToken redisRefreshToken = refreshTokenRedisRepository.findById(email)
//            .orElseThrow(() -> new UnAuthException());
//
//        Member member = memberRepository.findByEmail(email)
//            .orElseThrow(() -> new NotFoundException());
//
//        if (refreshToken.equals(redisRefreshToken.getRefreshToken())) {
//
//            String accessToken = tokenProvider.generateAccessToken(email,
//                redisRefreshToken.getMongKey());
//
//            redisRefreshToken.setAccessToken(accessToken);
//            refreshTokenRedisRepository.save(redisRefreshToken);
//
//            return LoginResDto.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .build();
//        }
//
//        throw new UnAuthException();
//    }
}
