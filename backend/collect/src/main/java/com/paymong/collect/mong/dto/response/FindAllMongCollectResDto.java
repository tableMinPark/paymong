package com.paymong.collect.mong.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class FindAllMongCollectResDto {

    List<MongDto> eggs;
    List<MongDto> level1;
    List<MongDto> level2;
    List<MongDto> level3;
}
