package com.paymong.common.global.client;

import com.paymong.common.global.vo.request.FindLastBuyReqVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "management")
public interface ManagementServiceClient {

    @GetMapping("/management/lastbuy")
    ResponseEntity<Object> findLastBuy(@RequestHeader(value = "MongId") String mongId, @SpringQueryMap FindLastBuyReqVo findLastBuyReqVo);
}
