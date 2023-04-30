package com.paymong.common.common.dto.response;

import com.paymong.common.common.entity.CommonCode;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class FindAllCommonCodeResDto {

    List<CommonCode> commonCodeList;
}
