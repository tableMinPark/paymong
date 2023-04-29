package com.paymong.common.common.dto.response;

import com.paymong.common.common.entity.CommonCode;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindAllCommonCodeResDto {

    List<CommonCode> commonCodeVoList;


}
