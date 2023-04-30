package com.paymong.collect.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStateCode {
    MAP_RUNTIME("001", "맵 컬렉션을 찾을 수 없습니다."),
    MONG_RUNTIME("002", "몽 컬렉션을 찾을 수 없습니다.");
    private final String code;
    private final String message;


}
