package com.paymong.management.global.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FindMongLevelCodeDto {
    private Integer tier;
    private Integer level;
    private Integer type;
}
