package com.paymong.collect.collect.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMapReqDto {

    private Long memberId;

    private String code;

}
