package com.paymong.battle.battle.vo.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MongStats {
    private Long mongId;
    private Double health;
    private Double strength;
    private Double weight;
}
