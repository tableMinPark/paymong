package com.paymong.management.mong.vo;

import com.paymong.management.mong.dto.FindMongReqDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMongReqVo {
    private Long memberId;

    public FindMongReqVo(FindMongReqDto findMongReqDto){
        this.memberId = findMongReqDto.getMemberId();
    }
}
