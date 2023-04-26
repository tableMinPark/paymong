package com.paymong.battle.battle.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BattleReadyByLocationResDto {
    private String battleRoomId;
    private String name;
    private String order;
}
