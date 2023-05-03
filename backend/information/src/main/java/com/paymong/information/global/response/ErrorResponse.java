package com.paymong.information.global.response;

import com.paymong.information.global.code.InformationStateCode;
import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse(InformationStateCode informationStateCode){
        this.code = informationStateCode.getCode();
        this.message = informationStateCode.getMessage();
    }
}
