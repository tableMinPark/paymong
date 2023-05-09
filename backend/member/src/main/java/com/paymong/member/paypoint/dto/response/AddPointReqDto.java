package com.paymong.member.paypoint.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPointReqDto {
    String content;
    Integer point;
    String code;
}
