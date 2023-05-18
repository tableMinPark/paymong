package com.paymong.common.common.dto.response;

import com.paymong.common.common.entity.CommonCode;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Food {

    private String name;
    private String foodCode;
    private Integer price;
    private LocalDateTime lastBuy;

    public static Food of(CommonCode commonCode, Integer price, LocalDateTime lastBuy) {

        if (lastBuy != null) {
            return Food.builder()
                .name(commonCode.getName())
                .foodCode(commonCode.getCode())
                .price(price)
                .lastBuy(lastBuy)
                .build();
        } else {
            return Food.builder()
                .name(commonCode.getName())
                .foodCode(commonCode.getCode())
                .price(price)
                .lastBuy(null)
                .build();
        }

    }
}
