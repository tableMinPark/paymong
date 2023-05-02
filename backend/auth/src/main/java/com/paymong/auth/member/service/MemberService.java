package com.paymong.auth.member.service;

import com.paymong.auth.auth.dto.request.LoginReqDto;
import com.paymong.auth.auth.dto.request.RegisterReqDto;
import com.paymong.auth.auth.dto.response.LoginResDto;
import com.paymong.auth.auth.entity.Auth;
import com.paymong.auth.auth.entity.Member;
import com.paymong.auth.auth.repository.AuthRepository;
import com.paymong.auth.auth.repository.MemberRepository;
import com.paymong.auth.auth.service.AuthService;
import com.paymong.auth.global.security.TokenInfo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private final AuthRepository authRepository;

    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResDto register(LoginReqDto loginReqDto) throws RuntimeException {
        String password = passwordEncoder.encode(UUID.randomUUID().toString());
        Member member = Member.builder().playerId(loginReqDto.getPlayerId()).password(password).build();
        memberRepository.save(member);
        Auth auth = Auth.of("USER", member);
        authRepository.save(auth);
        TokenInfo tokenInfo = authService.provideToken(member);
        return LoginResDto.builder()
            .accessToken(tokenInfo.getAcessToken())
            .refreshToken(tokenInfo.getRefreshToken())
            .build();
    }

//    @Transactional
//    public FindMemberInfoResDto findMemberInfo() throws RuntimeException {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        return new FindMemberInfoResDto();
    // mongId 가져오기
//        log.info("id : {}", member.getMemberId());
//    ObjectMapper om = new ObjectMapper();
//    FindMongResVo findMongResVo = null;
//        try {
//        findMongResVo = om.convertValue(
//            managementServiceClient.findMongByMember(new FindMongReqVo(member.getMemberId()))
//                .getBody(), FindMongResVo.class);
//    } catch (Exception e) {
//        log.info(e.getMessage());
//        return LoginResDto.builder()
//            .accessToken(accessToken)
//            .refreshToken(refreshToken)
//            .build();
//    }
//
//    Long mongId = findMongResVo.getMongId();
//        log.info("MongId : {}", findMongResVo.getMongId());
//    }

}
