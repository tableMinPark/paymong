package com.paymong.member.things.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class ThingsCommonCodeList {
    List<ThingsCommonCode> commonCodeDtoList;
}
