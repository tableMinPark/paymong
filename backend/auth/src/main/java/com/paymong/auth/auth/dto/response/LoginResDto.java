package com.paymong.auth.auth.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResDto {

    private String accessToken;
    private String refreshToken;

    private String role;
}
