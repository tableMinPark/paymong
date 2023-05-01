package com.paymong.common.common.dto.response;

import com.paymong.common.common.entity.CommonCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class FindEggResDto {

    private String code;
    private String name;

    public static FindEggResDto of(CommonCode commonCode) {
        return new FindEggResDto(commonCode.getCode(), commonCode.getName());
    }
}
