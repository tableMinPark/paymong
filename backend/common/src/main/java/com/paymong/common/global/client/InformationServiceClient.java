package com.paymong.common.global.client;

import com.paymong.common.common.dto.request.FindLastBuyReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "information")
public interface InformationServiceClient {

    @GetMapping("/information/lastbuy")
    ResponseEntity<Object> findLastBuy(@RequestHeader(value = "MongId") String mongId,
        @SpringQueryMap FindLastBuyReqDto findLastBuyReqDto);
}
