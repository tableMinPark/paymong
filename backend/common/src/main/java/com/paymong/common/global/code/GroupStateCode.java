package com.paymong.common.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupStateCode {
    FOOD("FD"),
    SNACK("SN");
    private final String code;

}
