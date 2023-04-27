package com.paymong.collect.global.response;

import com.paymong.collect.global.code.ErrorStateCode;
import lombok.Data;

@Data
public class ErrorResponse {

    private String code;
    private String message;

    public ErrorResponse(ErrorStateCode errorStateCode) {
        this.code = errorStateCode.getCode();
        this.message = errorStateCode.getMessage();
    }
}
