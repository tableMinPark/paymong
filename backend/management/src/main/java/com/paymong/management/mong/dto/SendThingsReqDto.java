package com.paymong.management.mong.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class SendThingsReqDto {
    private String thingsCode;
}
