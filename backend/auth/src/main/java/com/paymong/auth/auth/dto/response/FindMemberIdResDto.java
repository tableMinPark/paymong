package com.paymong.auth.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FindMemberIdResDto {
    Long memberId;
}
