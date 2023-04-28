package com.paymong.management.global.dto;

import com.paymong.management.global.code.ErrorCode;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private String code;
    private String message;
    public ErrorDto(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.name();
    }
}
