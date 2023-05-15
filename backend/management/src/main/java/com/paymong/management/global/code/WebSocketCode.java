package com.paymong.management.global.code;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum WebSocketCode implements BasicCode{
    SUCCESS("200","성공"),
    FAIL("201","불가능"),
    SOMNOLENCE("202","졸립니다."),
    HUNGRY("203","배고픕니다"),
    SICK("204","아픕니다"),
    POOP("205","똥이 많아요"),
    EVOLUTION_READY("206","진화대기"),
    DEATH_READY("207","죽을 수도 있습니다."),
    DEATH("208","죽었습니다."),
    MAP("209", "맵이 바뀝니다.")
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

    WebSocketCode(final String code, final String message){
        this.code = code;
        this.message = message;
    }

    public static WebSocketCode codeOf(String code){
        return Arrays.stream(values())
                .filter(value -> value.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

}
