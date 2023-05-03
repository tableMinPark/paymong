package com.paymong.auth.global.security;

import com.paymong.auth.auth.entity.Member;
import com.paymong.auth.auth.service.AuthService;
import com.paymong.auth.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String playerId) throws UsernameNotFoundException {
        Member member  = memberService.findByMemberPlayerId(playerId);
        return CustomUserDetail.of(member);
    }
}
