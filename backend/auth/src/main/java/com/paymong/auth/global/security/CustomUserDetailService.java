package com.paymong.auth.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.auth.auth.dto.request.SecureReqDto;
import com.paymong.auth.auth.dto.response.SecureResDto;
import com.paymong.auth.global.client.MemberServiceClient;
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

    private final MemberServiceClient memberServiceClient;

    @Override
    public UserDetails loadUserByUsername(String playerId) throws UsernameNotFoundException {
        SecureReqDto secureReqDto = SecureReqDto.builder().palyerId(playerId).build();
        ObjectMapper om = new ObjectMapper();
        SecureResDto secureResDto = om.convertValue(
            memberServiceClient.secure(secureReqDto).getBody(), SecureResDto.class);
        return CustomUserDetail.of(secureResDto);
    }
}
