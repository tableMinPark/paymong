package com.paymong.management.global.code;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NULL_POINT("000","빈 값이 있습니다."),
    NOT_FOUND("001", "해당 id를 가진 몽을 찾지 못했습니다."),
    NOT_ACTION("002", "해당 액션을 찾을 수 없습니다.");
    private final String code;
    private final String message;

    ErrorCode(final String code, final String message){
        this.code = code;
        this.message = message;
    }
}
