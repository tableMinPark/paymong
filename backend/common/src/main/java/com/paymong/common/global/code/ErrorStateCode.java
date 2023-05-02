package com.paymong.common.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStateCode {
    RUNTIME("500", "서버 에러"),
    NOTFOUND_GROUPCODE("001", "해당 그룹 코드를 찾을 수 없습니다."),
    NOTFOUND_COMMONCODE("002", "해당 코드를 찾을 수 없습니다."),
    NOTFOUND_FOODCODE("003", "해당 음식 코드를 찾을 수 없습니다.");

    private final String code;
    private final String message;

}
