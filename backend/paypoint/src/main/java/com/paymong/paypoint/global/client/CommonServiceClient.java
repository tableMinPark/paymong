package com.paymong.paypoint.global.client;

import com.paymong.paypoint.paypoint.vo.FindMapByNameReqVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "common")
public interface CommonServiceClient {
    @GetMapping("/common/name")
    ResponseEntity<Object> findMapByName(@SpringQueryMap FindMapByNameReqVo findMapByNameReqVo);
}
