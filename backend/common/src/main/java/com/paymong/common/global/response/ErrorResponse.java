package com.paymong.common.global.response;

import com.paymong.common.global.code.CommonStateCode;
import lombok.Data;

@Data
public class ErrorResponse {

    private String code;
    private String message;

    public ErrorResponse(CommonStateCode errorStateCode) {
        this.code = errorStateCode.getCode();
        this.message = errorStateCode.getMessage();
    }
}
