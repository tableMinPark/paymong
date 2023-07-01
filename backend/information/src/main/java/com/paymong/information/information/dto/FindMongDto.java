package com.paymong.information.information.dto;

import com.paymong.information.information.entity.Mong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindMongDto {

    private Long mongId;
    private String name;
    private String mongCode;
    private String stateCode;
    private Integer poopCount;
    private String mapCode;


    public FindMongDto(Mong mong, String mapCode){
        this.mongId = mong.getMongId();
        this.name = mong.getName();
        this.mongCode = mong.getCode();
        this.stateCode = mong.getStateCode();
        this.poopCount = mong.getPoopCount();
        this.mapCode= mapCode;
    }
}
