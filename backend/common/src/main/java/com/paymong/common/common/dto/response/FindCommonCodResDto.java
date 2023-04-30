package com.paymong.common.common.dto.response;

import com.paymong.common.common.entity.CommonCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class FindCommonCodResDto {

    private CommonCode commonCodeVo;

}
