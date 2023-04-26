package com.paymong.battle.battle.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BattleReadyByLocationReqDto {
    private Double latitude;
    private Double longitude;
}
