package com.paymong.management.training.dto;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalkingReqDto {
    private Integer walkingCount;
}
