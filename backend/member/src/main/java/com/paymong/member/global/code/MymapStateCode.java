package com.paymong.member.global.code;

public enum MymapStateCode implements BasicCode{
	SUCCESS("200","성공"),
    NOTEXIST("400", "존재하지 않는 ID"),
    MYMAP_ERROR("411", "존재하지않는 Mymap"),
    UNAVAILABLE("503","처리할 수 없음"),

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

    MymapStateCode(final String code, final String message){
        this.code = code;
        this.message = message;
    }
}
