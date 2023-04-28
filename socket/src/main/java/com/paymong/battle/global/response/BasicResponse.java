package com.paymong.battle.global.response;

import com.paymong.battle.global.code.BasicCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BasicResponse {
    private BasicHeader header;
    private Object body;

    public static BasicResponse Body (BasicCode code, Object body){
        return BasicResponse.builder()
                .header(BasicHeader.Header(code.getCode(), code.getMessage()))
                .body(body)
                .build();
    }
}

