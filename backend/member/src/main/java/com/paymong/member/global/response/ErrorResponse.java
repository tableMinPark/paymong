package com.paymong.member.global.response;

import com.paymong.member.global.code.BasicCode;
import com.paymong.member.global.code.PaypointStateCode;
import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse(BasicCode managementStateCode){
        this.code = managementStateCode.getCode();
        this.message = managementStateCode.getMessage();
    }
}
