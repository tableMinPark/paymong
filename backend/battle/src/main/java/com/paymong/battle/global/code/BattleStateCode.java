package com.paymong.battle.global.code;

public enum BattleStateCode implements BasicCode {
    SUCCESS("200", "배틀 기능 성공"),
    BATTLE_END("400", "연결해제로 인한 배틀 종료"),
    FAIL("500", "배틀 기능 실패"),
    MATCHING_FAIL("501", "매칭 실패"),
    FIND_BATTLE_ROOM_FAIL("502", "배틀방 조회 실패"),
    BATTLE_FAIL("503", "대전 실패");

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
