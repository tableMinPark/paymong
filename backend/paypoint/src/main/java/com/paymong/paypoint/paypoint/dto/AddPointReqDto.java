package com.paymong.paypoint.paypoint.dto;

import lombok.Data;

@Data
public class AddPointReqDto {
    String content;
    int price;
    Long mongId;
    Long memberId;
}
