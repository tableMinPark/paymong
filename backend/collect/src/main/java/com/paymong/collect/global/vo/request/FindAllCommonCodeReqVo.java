package com.paymong.collect.global.vo.request;

import com.paymong.collect.global.code.GroupStateCode;
import lombok.Data;

@Data
public class FindAllCommonCodeReqVo {

    String groupCode;

    public FindAllCommonCodeReqVo(GroupStateCode groupCode) {
        this.groupCode = groupCode.getCode();
    }
}
