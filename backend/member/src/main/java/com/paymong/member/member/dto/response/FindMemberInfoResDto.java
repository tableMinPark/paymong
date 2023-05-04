package com.paymong.member.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMemberInfoResDto {
    Long mongId;

    Integer point;
}
