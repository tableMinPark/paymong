package com.paymong.paypoint.global.client;

import com.paymong.paypoint.paypoint.dto.AddMapReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "collect")
public interface CollectServiceClient {
    @PostMapping(value = "/collect/map", produces = "application/json")
    ResponseEntity<Object> addMap(@RequestHeader("MemberId") String memberId,
                                  @RequestHeader("MongId") String mongId,
                                  @RequestBody AddMapReqDto addMapReqDto);
}
