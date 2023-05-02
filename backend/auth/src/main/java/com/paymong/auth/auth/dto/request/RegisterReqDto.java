package com.paymong.auth.auth.dto.request;

import com.paymong.auth.auth.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterReqDto {

    private String playerId;

    public Member toMember(){
        return Member.builder()
            .playerId(this.playerId)
            .build();
    }
}
