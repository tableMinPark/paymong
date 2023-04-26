package com.paymong.battle.battle.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterStats {
    private Long characterId;
    private Double health;
    private Integer strength;
    private Integer weight;
}
