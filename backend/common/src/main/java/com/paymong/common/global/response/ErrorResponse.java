package com.paymong.common.global.response;

import com.paymong.common.global.code.ErrorStateCode;
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
