package com.paymong.management.mong.vo;

import com.paymong.management.mong.entity.Mong;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMongResVo {
    private String name;
    private String mongCode;
    private Integer weight;
    private LocalDateTime born;

    public AddMongResVo(Mong mong){
        this.name = mong.getName();
        this.mongCode = mong.getCode();
        this.weight = mong.getWeight();
        this.born = mong.getRegDt();
    }
}
