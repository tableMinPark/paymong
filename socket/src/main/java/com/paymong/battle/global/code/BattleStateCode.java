package com.paymong.battle.global.code;

public enum BattleStateCode implements BasicCode {
    SUCCESS("200", "배틀 기능 성공"),
    
    FAIL("500", "배틀 기능 실패");

    private String code;
    private String message;

    @Override
    public String getCode() { return this.code; }
    @Override
    public String getMessage() { return this.message; }

    BattleStateCode(String code, String message){
        this.code = code;
        this.message = message;
    }
}
