package com.paymong.auth.global.response;

import com.paymong.auth.global.code.ErrorStateCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String code;
    private String message;
    public ErrorResponse(ErrorStateCode errorStateCode) {
        this.code = errorStateCode.getCode();
        this.message = errorStateCode.getMessage();
    }
}
