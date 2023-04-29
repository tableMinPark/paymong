package com.paymong.common.global.vo.response;

import com.paymong.common.common.entity.CommonCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonCodeVo {

    private String code;
    private String name;
    private GroupCodeVo groupCodeVo;

    public static CommonCodeVo of(CommonCode commonCode){
        return CommonCodeVo.builder()
            .code(commonCode.getCode())
            .name(commonCode.getName())
            .groupCodeVo(GroupCodeVo.builder()
                .code(commonCode.getCode())
                .name(commonCode.getName())
                .build())
            .build();
    }
}
