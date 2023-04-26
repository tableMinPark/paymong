package com.paymong.battle.battle.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResDto {
    private Long memberId;
    private Long characterId;
}
