package com.paymong.management.global.code;

import java.util.Arrays;

public enum MongActiveCode implements BasicCode {

    MEAL("AT000", "식사"),
    SNACK("AT001", "간식"),
    BOWEL("AT002", "배변"),
    SLEEP("AT003", "수면"),
    WALKING("AT004", "산책"),
    TRAINING("AT005", "훈련"),
    STROKE("AT006", "쓰다듬기"),
    AWAKE("AT007", "기상"),
    CLEAN("AT008", "청소"),
    EVOLUTION("AT009", "진화"),
    GRADUATION("AT010", "졸업");
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

    MongActiveCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static MongActiveCode codeOf(String code) {
        return Arrays.stream(values())
                .filter(value -> value.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
