package com.paymong.common.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindRandomMongReqDto {

    //tier : 등급
    //level : 몇 단계 요청
    //type : 몽 종류
    private Integer tier;
    private Integer level;
    private Integer type;
}
