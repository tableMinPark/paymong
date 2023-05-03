package com.paymong.paypoint.global.client;

import com.paymong.paypoint.paypoint.dto.FindMapByNameReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "common")
public interface CommonServiceClient {
    @PostMapping(value = "/common/name", produces = "application/json")
    ResponseEntity<Object> findMapByName(@RequestHeader("MemberId") String memberId,
                                         @RequestHeader("MongId") String mongId,
                                         @RequestBody FindMapByNameReqDto findMapByNameReqVo);
}
