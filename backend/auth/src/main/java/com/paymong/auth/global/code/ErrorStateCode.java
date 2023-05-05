package com.paymong.auth.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStateCode {

    UNAUTHORIXED("401", "권한이 없습니다"),
    ACCESS_TOKEN_EXPIRE("403", "ACCESS 토큰 만료"),
    REFRESH_TOKEN_EXPIRE("404", "REFRESH 토큰 만료"),
    REDIS("408", "캐시 서버에 문제가 있습니다"),
    RUNTIME("500", "서버 에러"),
    TOKEN_INVALID("400", "토큰 형식이 잘못되었습니다"),
    TOKEN_UNAUTH("402","토큰이 일치하지 않습니다"),
    SUCCESS("200", "성공");
    private final String code;
    private final String message;


}
