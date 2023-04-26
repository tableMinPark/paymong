package com.paymong.battle.battle.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthReqDto {
    private String accessToken;
}
