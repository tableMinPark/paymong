package com.paymong.auth.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtStateCode {
    ACCESS_TOKEN_EXPIRATION_PERIOD(1000L * 60L * 60L * 24L),
    REFRESH_TOKEN_EXPIRATION_PERIOD(1000L * 60L * 60L * 24L);

    private Long value;

}
