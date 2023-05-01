package com.paymong.management.mong.dto;

import com.paymong.management.mong.vo.AddMongResVo;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMongResDto {
    private String name;
    private String mongCode;
    private Integer weight;
    private LocalDate born;
    public AddMongResDto(AddMongResVo addMongResVo){
        this.name = addMongResVo.getName();
        this.mongCode = addMongResVo.getMongCode();
        this.weight = addMongResVo.getWeight();
        this.born = addMongResVo.getBorn();
    }
}
