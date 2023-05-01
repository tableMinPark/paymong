package com.paymong.management.global.response;

import com.paymong.management.global.code.ManagementStateCode;
import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse(ManagementStateCode managementStateCode){
        this.code = managementStateCode.getCode();
        this.message = managementStateCode.getMessage();
    }
}
