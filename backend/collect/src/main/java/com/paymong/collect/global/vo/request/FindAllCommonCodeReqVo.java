package com.paymong.collect.global.vo.request;

import com.paymong.collect.global.code.GroupCode;
import lombok.Data;

@Data
public class FindAllCommonCodeReqVo {

    String groupCode;

    public FindAllCommonCodeReqVo(GroupCode groupCode) {
        this.groupCode = groupCode.getCode();
    }
}
