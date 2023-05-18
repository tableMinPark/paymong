package com.paymong.battle.battle.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindMongBattleResDto {
    private Long mongId;
    private String name;
    private Double strength;
    private Double weight;
}
