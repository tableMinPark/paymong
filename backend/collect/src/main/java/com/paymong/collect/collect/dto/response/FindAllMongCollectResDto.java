package com.paymong.collect.collect.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;


@Data
@Builder
public class FindAllMongCollectResDto {

    List<MongDto> eggs;
    List<MongDto> level1;
    List<MongDto> level2;
    List<MongDto> level3;
}
