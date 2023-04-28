package com.paymong.auth.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStateCode {

    NOTFOUNDUSER("001", "사용자를 찾을 수 없습니다"),
    UNAUTHORIXED("002", "권한이 없습니다"),
    RUNTIME("003", "서버 에러");
    private final String code;
    private final String message;


}
