package com.paymong.battle.battle.dto.request;

import com.paymong.battle.global.code.MessageType;
import lombok.Data;

@Data
public class BattleMessageReqDto {
    private MessageType type;
    private Long characterId;
    private Double latitude;
    private Double longitude;
    private String battleRoomId;
    private String order;
}
