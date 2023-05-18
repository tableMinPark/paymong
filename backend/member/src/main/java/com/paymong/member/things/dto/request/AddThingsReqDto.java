package com.paymong.member.things.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddThingsReqDto {
    String thingsName;
    String thingsCode;
    String routine;
}
