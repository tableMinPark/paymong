package com.paymong.management.feed.vo;

import com.paymong.management.feed.dto.FeedFoodReqDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedFoodReqVo {
    private String foodCode;

    public FeedFoodReqVo(FeedFoodReqDto feedFoodReqDto){
        this.foodCode = feedFoodReqDto.getFoodCode();
    }
}
