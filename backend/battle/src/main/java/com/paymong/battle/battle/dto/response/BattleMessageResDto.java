package com.paymong.battle.battle.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BattleMessageResDto {
    private String battleRoomId;
    private String mongCodeA;
    private String mongCodeB;
    private Integer nowTurn;
    private Integer totalTurn;
    private String nextAttacker;
    private String order;
    private Double damageA;
    private Double damageB;
    private Double healthA;
    private Double healthB;
}
