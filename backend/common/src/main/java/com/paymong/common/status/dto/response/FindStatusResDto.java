package com.paymong.common.status.dto.response;

import com.paymong.common.status.entitiy.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class FindStatusResDto {

    private String code;
    private Integer point;
    private Integer strength;
    private Integer health;
    private Integer satiety;
    private Integer sleep;
    private Integer weight;

    public static FindStatusResDto of(Status status) {
        return FindStatusResDto.builder()
            .code(status.getCode())
            .point(status.getPoint())
            .strength(status.getStrength())
            .health(status.getHealth())
            .satiety(status.getSatiety())
            .sleep(status.getSleep())
            .weight(status.getWeight())
            .build();
    }
}
