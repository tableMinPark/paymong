package com.paymong.information.global.code;

import lombok.Getter;

@Getter
public enum InformationStateCode implements BasicCode{
    SUCCESS("200","성공"),
    NULL_POINT("500","빈 값이 있습니다."),
    NOT_FOUND("501", "해당 id를 가진 몽을 찾지 못했습니다."),
    NOT_ACTION("502", "해당 액션을 찾을 수 없습니다."),
    UNKNOWN("555", "알 수 없음");
    private final String code;
    private final String message;

    @Override
    public String getCode(){
        return this.code;
    }

    @Override
    public String getMessage(){
        return this.message;
    }

    InformationStateCode(final String code, final String message){
        this.code = code;
        this.message = message;
    }
}
