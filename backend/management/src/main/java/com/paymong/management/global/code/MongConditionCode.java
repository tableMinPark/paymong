package com.paymong.management.global.code;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MongConditionCode implements BasicCode{
    NORMAL("CD000","정상"),
    SICK("CD001","아픔"),
    SLEEP("CD002","수면"),
    SOMNOLENCE("CD003","졸림"),
    HUNGRY("CD004","배고픔"),
    DIE("CD005","죽음"),
    GRADUATE("CD006","졸업"),
    EVOLUTION_READY("CD007","진화대기")
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

    public static MongConditionCode codeOf(String code){
        return Arrays.stream(values())
                .filter(value -> value.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

}
