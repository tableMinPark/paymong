package com.paymong.common.common.dto.response;

import com.paymong.common.common.entity.CommonCode;
import com.paymong.common.global.code.GroupStateCode;
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

    public static Food of(CommonCode commonCode, LocalDateTime lastBuy) {
        int price = 0;
        char grade = commonCode.getCode().charAt(3);
        if (commonCode.getGroupCode().equals(GroupStateCode.FOOD)) {
            switch (grade) {
                case '0':
                    price = 100;
                    break;
                case '1':
                    price = 500;
                    break;
                case '2':
                    price = 1000;
                    break;
            }
        } else if (commonCode.getGroupCode().equals(GroupStateCode.SNACK)) {
            switch (grade) {
                case '0':
                    price = 300;
                    break;
                case '1':
                    price = 600;
                    break;
            }
        }
        return Food.builder()
            .name(commonCode.getName())
            .foodCode(commonCode.getCode())
            .price(price)
            .lastBuy(lastBuy)
            .build();
    }
}
