package com.paymong.management.global.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NextLevelDto {
    private Long mongId;
    private Integer level;
    private Integer type;
}
