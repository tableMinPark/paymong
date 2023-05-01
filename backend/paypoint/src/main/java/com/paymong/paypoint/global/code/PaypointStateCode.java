package com.paymong.paypoint.global.code;

import lombok.Getter;

@Getter
public enum PaypointStateCode implements BasicCode{
    SUCCESS("200","성공"),

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

    PaypointStateCode(final String code, final String message){
        this.code = code;
        this.message = message;
    }
}
