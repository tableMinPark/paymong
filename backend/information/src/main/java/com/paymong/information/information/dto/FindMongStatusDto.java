package com.paymong.information.information.dto;

import com.paymong.information.information.entity.Mong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMongStatusDto {
    private Long mongId;
    private String name;
    private Double health;
    private Double satiety;
    private Double strength;
    private Double sleep;

    public FindMongStatusDto(Mong mong){
        this.mongId = mong.getMongId();
        this.name = mong.getName();
    }
}
