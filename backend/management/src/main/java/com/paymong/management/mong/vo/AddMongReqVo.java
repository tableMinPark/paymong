package com.paymong.management.mong.vo;

import com.paymong.management.mong.dto.AddMongReqDto;
import lombok.*;

import java.time.LocalTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMongReqVo {
    private String name;
    private LocalTime sleepStart;
    private LocalTime sleepEnd;

    public AddMongReqVo(AddMongReqDto addMongReqDto){
        this.name = addMongReqDto.getName();
        this.sleepStart = addMongReqDto.getSleepStart();
        this.sleepEnd = addMongReqDto.getSleepEnd();
    }
}
