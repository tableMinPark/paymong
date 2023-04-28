package com.paymong.collect.global.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CommonCodeResVo {
    private String code;
    private String name;
    private String group_code;
    private String group_name;
}
