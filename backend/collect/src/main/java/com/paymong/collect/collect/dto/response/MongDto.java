package com.paymong.collect.collect.dto.response;

import com.paymong.collect.global.vo.response.CommonCodeResVo;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MongDto {
    private Boolean isOpen;
    private String name;
    private String characterCode;

    public static MongDto of(CommonCodeResVo commonCodeResVo){
        return MongDto.builder()
            .isOpen(false)
            .name(commonCodeResVo.getName())
            .characterCode(commonCodeResVo.getCode())
            .build();
    }

//    public static void isValid

}
