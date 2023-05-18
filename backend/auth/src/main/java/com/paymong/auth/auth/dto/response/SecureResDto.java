package com.paymong.auth.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecureResDto {
    private Long memberId;

    private Integer point;

    private String password;

    private String playerId;

}
