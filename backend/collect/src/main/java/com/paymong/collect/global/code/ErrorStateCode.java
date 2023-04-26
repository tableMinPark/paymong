package com.paymong.collect.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStateCode {
    RUNTIME("500", "서버 에러");
    private final String code;
    private final String message;


}
