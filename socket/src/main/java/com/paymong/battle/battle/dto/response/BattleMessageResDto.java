package com.paymong.battle.battle.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BattleMessageResDto {
    private Integer nowTurn;
    private String nextAttacker;
    private Double healthA;
    private Double healthB;
    private Double damageA;
    private Double damageB;
}
