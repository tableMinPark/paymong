package com.paymong.management.global.code;

import lombok.Getter;

@Getter
public enum ManagementStateCode implements BasicCode{
    SUCCESS("200","성공"),
    CANT_STROKE("201","쓰다듬을 수 없음"),
    NULL_POINT("500","빈 값이 있습니다."),
    NOT_FOUND("501", "해당 id를 가진 몽을 찾지 못했습니다."),
    NOT_ACTION("502", "해당 액션을 찾을 수 없습니다."),
    UNKNOWN("503", "알 수 없음"),
    GATEWAY_ERROR("504", "게이트웨이 통신 에러 입니다."),
    ALREADY_EXIST("505", "이미 몽이 존재합니다."),
    UNSUITABLE("506", "적절하지 않은 몽입니다.");

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

    ManagementStateCode(final String code, final String message){
        this.code = code;
        this.message = message;
    }
}
