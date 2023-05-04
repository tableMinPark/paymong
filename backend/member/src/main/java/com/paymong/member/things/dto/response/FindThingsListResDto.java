package com.paymong.member.things.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindThingsListResDto {
    Long thingsId;
    String thingsCode;
    String thingsName;
    String routine;
    LocalDateTime regDt;

}
