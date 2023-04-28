package com.paymong.auth.auth.dto.request;

import com.paymong.auth.auth.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterReqDto {

    private String email;
    private String password;

    public Member toMember(){
        return Member.builder()
            .email(this.email)
            .password(this.password)
            .build();
    }
}
