package com.paymong.information.information.dto;

import com.paymong.information.information.entity.Mong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMongInfoDto {
    private Long mongId;
    private String name;
    private Integer weight;
    private LocalDateTime born;

    public FindMongInfoDto(Mong mong){
        this.mongId = mong.getMongId();
        this.name = mong.getName();
        this.weight = mong.getWeight();
        this.born = mong.getRegDt();
    }
}
