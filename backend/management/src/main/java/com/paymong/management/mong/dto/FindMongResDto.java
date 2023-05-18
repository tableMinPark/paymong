package com.paymong.management.mong.dto;

import com.paymong.management.mong.vo.FindMongResVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindMongResDto {
    private Long mongId;

    public FindMongResDto(FindMongResVo findMongResVo){
        this.mongId = findMongResVo.getMongId();
    }
}
