package com.paymong.collect.map.dto.response;

import com.paymong.collect.global.vo.response.CommonCodeDto;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class FindAllMapCollectResDto {

    private Boolean isOpen;
    private String name;
    private String mapCode;

    public static FindAllMapCollectResDto of(CommonCodeDto commonCodeDto) {
            return FindAllMapCollectResDto.builder()
                .name(commonCodeDto.getName())
                .mapCode(commonCodeDto.getCode())
                .isOpen(false)
                .build();
    }


    public FindAllMapCollectResDto isContain(List<String> mapCollect) {
        if (mapCollect.contains(this.mapCode) || this.mapCode.equals("MP000")) {
            this.setIsOpen(true);
        } else {
            this.setIsOpen(false);
            this.setMapCode(null);
            this.setName(null);
        }
        return this;
    }

}
