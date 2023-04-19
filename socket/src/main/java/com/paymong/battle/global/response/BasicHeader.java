package com.paymong.battle.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
public class BasicHeader {
    private String code;
    private String message;

    public static BasicHeader Header(String code, String message){
        return BasicHeader.builder()
                .code(code)
                .message(message)
                .build();
    }
}
