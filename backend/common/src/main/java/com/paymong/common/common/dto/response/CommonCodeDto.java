package com.paymong.common.common.dto.response;

import com.paymong.common.common.entity.CommonCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonCodeDto {
    private String code;
    private String name;

    public static CommonCodeDto of(CommonCode commonCode){
        return CommonCodeDto.builder()
            .code(commonCode.getCode())
            .name(commonCode.getName())
            .build();
    }
}
