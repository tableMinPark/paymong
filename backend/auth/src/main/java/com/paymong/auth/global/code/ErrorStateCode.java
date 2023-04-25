package com.paymong.auth.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStateCode {

    NOTFOUNDUSER_EXCEPTION("001", "사용자를 찾을 수 없습니다"),
    UNAUTHORIXED_EXCEPTION("002", "권한이 없습니다"),
    RUNTIME_EXCEPTION("003", "서버 에러");
    private final String code;
    private final String message;


}
