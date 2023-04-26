package com.paymong.battle.battle.service;

import com.paymong.battle.battle.dto.response.AuthResDto;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public AuthResDto findMemberInfo(String accessToken) {
        /*
            accessToken으로 memberId, characterId 받아와야함
         */
        return AuthResDto.builder()
                .memberId(1L)
                .characterId(2L)
                .build();
    }
}
