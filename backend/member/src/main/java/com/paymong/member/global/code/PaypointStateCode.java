package com.paymong.member.global.code;

import lombok.Getter;

@Getter
public enum PaypointStateCode implements BasicCode{
    SUCCESS("200","성공"),
    NOTEXIST("400", "존재하지 않는 ID"),
    UNAVAILABLE("503","처리할 수 없음"),
    FAIL_AUTH("520","AUTH 통신 실패"),
    FAIL_COMMON("521","COMMON 통신 실패"),
    FAIL_COLLECT("522","COLLECT 통신 실패"),
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
