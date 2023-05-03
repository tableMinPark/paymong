package com.paymong.auth.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMemberInfoResDto {
    Long mongId;

    Long point;
}
