package com.paymong.information.global.code;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public enum MongConditionCode implements BasicCode{
    NORMAL("CD000","정상"),
    SICK("CD001","아픔"),
    SLEEP("CD002","수면"),
    SOMNOLENCE("CD003","졸림"),
    HUNGRY("CD004","배고픔"),
    DIE("CD005","죽음")
    ;
    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    MongConditionCode(final String code, final String message){
        this.code = code;
        this.message = message;
    }

}
