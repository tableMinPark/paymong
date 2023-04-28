package com.paymong.auth.global.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenInfo {

    private String acessToken;
    private String refreshToken;

}
