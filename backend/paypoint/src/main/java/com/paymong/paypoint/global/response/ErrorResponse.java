package com.paymong.paypoint.global.response;

import com.paymong.paypoint.global.code.PaypointStateCode;
import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse(PaypointStateCode managementStateCode){
        this.code = managementStateCode.getCode();
        this.message = managementStateCode.getMessage();
    }
}
