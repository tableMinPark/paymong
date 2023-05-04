package com.paymong.member.member.dto.response;

import com.paymong.member.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecureResDto {
    private String memberId;

    private Integer point;

    private String password;

    private String playerId;

    private String mapCode;

    public SecureResDto of(Member member)  {
        return SecureResDto.builder()
            .mapCode(member.getMapCode())
            .memberId(member.getMemberId())
            .password(member.getPassword())
            .playerId(member.getPlayerId())
            .point(member.getPoint())
            .build();
    }
}
