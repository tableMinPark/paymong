package com.paymong.battle.information.dto.response;

import lombok.Data;

@Data
public class FindCharacterResponse {
    private Long characterId;
    private Integer strength;
    private Integer weight;
}
