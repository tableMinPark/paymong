package com.paymong.management.training.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingReqVo {
    private Long mongId;
    private Long memberId;
    private Integer walkingCount;
}
