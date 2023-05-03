package com.paymong.common.common.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindAllCommonCodeResDto {
    List<CommonCodeDto>  commonCodeDtoList;
}
