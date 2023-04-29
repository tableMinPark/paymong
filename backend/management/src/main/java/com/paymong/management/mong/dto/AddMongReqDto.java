package com.paymong.management.mong.dto;

import lombok.*;

import java.time.LocalTime;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddMongReqDto {
    private String name;
    private LocalTime sleepStart;
    private LocalTime sleepEnd;
}
