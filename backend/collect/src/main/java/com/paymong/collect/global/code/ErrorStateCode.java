package com.paymong.collect.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStateCode {
    BAD_GATEWAY("502", "게이트 웨이 에러"),
    COMMONCODE("503", "공통 코드 조회에 오류가 있습니다"),
    RUNTIME("500", "서버 에러"),

    SUCCESS("200","성공");
    private final String code;
    private final String message;


}
