package com.paymong.collect.collect.dto.response;

import lombok.Builder;

@Builder
public class FindAllMapCollectResDto {

    private Boolean isOpen;
    private String name;
    private String mapCode;

}
