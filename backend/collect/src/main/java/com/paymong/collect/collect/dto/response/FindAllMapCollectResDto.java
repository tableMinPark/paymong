package com.paymong.collect.collect.dto.response;

import com.paymong.collect.global.vo.response.MapCodeResVo;
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

    public static boolean isVaildMapCode(MapCodeResVo mapCodeResVo) {
        if (!mapCodeResVo.getCode().equals("MP000")) {
            return true;
        } else {
            return false;
        }
    }

    public static FindAllMapCollectResDto ofMapCode(MapCodeResVo mapCodeResVo) {
        return FindAllMapCollectResDto.builder()
            .name(mapCodeResVo.getName())
            .mapCode(mapCodeResVo.getCode())
            .isOpen(false)
            .build();
    }

    public FindAllMapCollectResDto isContain(List<String> mapCollect) {
        if (mapCollect.contains(this.mapCode)) {
            this.setIsOpen(true);
        } else {
            this.setIsOpen(false);
            this.setMapCode(null);
            this.setName(null);
        }
        return this;
    }

}
