package com.paymong.collect.global.client;

import com.paymong.collect.global.vo.request.FindAllCommonCodeReqVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "common")
public interface CommonServiceClient {

    @GetMapping("/common/list")
    ResponseEntity<Object> findAllCommonCode(@SpringQueryMap FindAllCommonCodeReqVo findAllCommonCodeReqVo);
}
