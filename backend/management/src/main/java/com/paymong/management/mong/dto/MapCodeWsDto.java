package com.paymong.management.mong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapCodeWsDto {
    private Long memberId;
    private String mapCode;
}
