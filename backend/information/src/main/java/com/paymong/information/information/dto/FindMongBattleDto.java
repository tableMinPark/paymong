package com.paymong.information.information.dto;

import com.paymong.information.information.entity.Mong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMongBattleDto {
    private Long mongId;
    private String name;
    private Double strength;
    private Double weight;

    public FindMongBattleDto(Mong mong){
        this.mongId = mong.getMongId();
        this.name = mong.getName();
        this.strength = Double.valueOf(mong.getStrength());
        this.weight = Double.valueOf(mong.getWeight());
    }
}
