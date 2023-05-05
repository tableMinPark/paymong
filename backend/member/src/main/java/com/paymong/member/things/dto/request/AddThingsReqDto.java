package com.paymong.member.things.dto.request;

import lombok.Data;

@Data
public class AddThingsReqDto {
    String thingsName;
    String thingsCode;
    String routine;
}
