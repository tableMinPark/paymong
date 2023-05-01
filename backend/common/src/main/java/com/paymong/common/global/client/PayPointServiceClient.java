package com.paymong.common.global.client;

import com.paymong.common.global.vo.request.FindLastBuyReqVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paypoint")
public interface PayPointServiceClient {

    @PostMapping("/paypoint/lastbuy")
    ResponseEntity<Object> findLastBuy(@RequestBody FindLastBuyReqVo findLastBuyReqVo);
}
