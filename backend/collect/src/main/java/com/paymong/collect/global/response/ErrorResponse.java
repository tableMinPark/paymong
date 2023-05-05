package com.paymong.collect.global.response;

import com.paymong.collect.global.code.CollectStateCode;
import lombok.Data;

@Data
public class ErrorResponse {

    private String code;
    private String message;

    public ErrorResponse(CollectStateCode errorStateCode) {
        this.code = errorStateCode.getCode();
        this.message = errorStateCode.getMessage();
    }
}
