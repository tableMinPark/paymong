package com.paymong.management.poop.vo;

import com.paymong.management.mong.entity.Mong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PoopMongResVo {
    private String stateCode;
    private Integer poopCount;

    public PoopMongResVo(Mong mong){
        this.stateCode = mong.getStateCode();
        this.poopCount = mong.getPoopCount();
    }
}
