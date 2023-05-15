package com.paymong.management.global.socket.dto;

import com.paymong.management.global.code.WebSocketCode;
import com.paymong.management.mong.dto.SendPointResDto;
import com.paymong.management.mong.dto.SendThingsResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PointDto {
    private String code;
    private String message;
    private Integer point;

    public PointDto(SendPointResDto sendPointResDto, WebSocketCode webSocketCode){
        this.code = webSocketCode.getCode();
        this.message = webSocketCode.getMessage();
        this.point = sendPointResDto.getPoint();
    }
}
