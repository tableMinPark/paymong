package com.paymong.management.mong.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindMongReqDto {
    private Long memberId;

//    private FindMongReqDto(Long memberId){
//        this.memberId = memberId;
//    }
//    public static FindMongReqDto of(final Long memberId){
//        return new FindMongReqDto(memberId);
//    }
}
