package com.paymong.management.feed.vo;

import com.paymong.management.feed.dto.FeedFoodReqDto;
import com.paymong.management.feed.dto.FeedSnackReqDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedSnackReqVo {
    private Long mongId;
    private String snackCode;

    public FeedSnackReqVo(FeedSnackReqDto feedSnackReqDto){
        this.snackCode = feedSnackReqDto.getSnackCode();
    }
}
