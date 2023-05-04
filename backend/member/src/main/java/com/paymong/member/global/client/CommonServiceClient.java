package com.paymong.member.global.client;

import com.paymong.member.paypoint.dto.FindMapByNameReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "common")
public interface CommonServiceClient {
    @GetMapping(value = "/common/name", produces = "application/json")
    ResponseEntity<Object> findMapByName(@RequestHeader("MemberId") String memberId,
                                         @SpringQueryMap FindMapByNameReqDto findMapByNameReqVo);
}
