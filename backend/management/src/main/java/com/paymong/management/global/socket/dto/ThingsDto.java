package com.paymong.management.global.socket.dto;

import com.paymong.management.global.code.WebSocketCode;
import com.paymong.management.mong.dto.SendThingsReqDto;
import com.paymong.management.mong.dto.SendThingsResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThingsDto {
    private String code;
    private String message;
    private String thingsCode;

    public ThingsDto(SendThingsResDto sendThingsResDto, WebSocketCode webSocketCode){
        this.code = webSocketCode.getCode();
        this.message = webSocketCode.getMessage();
        this.thingsCode = sendThingsResDto.getThingsCode();
    }
}
