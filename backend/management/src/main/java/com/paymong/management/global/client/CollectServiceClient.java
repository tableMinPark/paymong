package com.paymong.management.global.client;

import com.paymong.management.global.dto.FindCommonCodeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("collect")
public interface CollectServiceClient {
    @PostMapping("/collect/mong")
    public ResponseEntity<Object> addMong(@RequestHeader(value = "MemberId") String memberIdStr,
                                          @RequestBody FindCommonCodeDto findCommonCodeDto);
}
