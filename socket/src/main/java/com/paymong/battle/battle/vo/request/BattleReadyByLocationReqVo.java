package com.paymong.battle.battle.vo.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BattleReadyByLocationReqVo {
    private Long characterId;
    private Double latitude;
    private Double longitude;
}
