package com.paymong.management.status.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FindStatusResDto {
    private String code;
    private Integer point;
    private Integer strength;
    private Integer health;
    private Integer satiety;
    private Integer sleep;
    private Integer weight;

}
