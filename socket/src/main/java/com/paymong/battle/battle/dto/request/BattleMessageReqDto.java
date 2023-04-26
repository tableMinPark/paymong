package com.paymong.battle.battle.dto.request;

import lombok.Data;

@Data
public class BattleMessageReqDto {
    public enum MessageType {
        ENTER, LEFT, RIGHT
    }

    private MessageType type;
    private String battleRoomId;
    private Long characterId;
    private String order;
}
