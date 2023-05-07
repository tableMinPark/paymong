package com.paymong.member.global.client;

import com.paymong.member.paypoint.dto.request.FindMapByNameReqDto;
import com.paymong.member.things.dto.request.FindAddableThingsReqDto;
import com.paymong.member.things.dto.response.ThingsCommonCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@FeignClient(name = "common")
public interface CommonServiceClient {
    @GetMapping(value = "/common/name", produces = "application/json")
    ResponseEntity<Object> findMapByName(@RequestHeader("MemberId") String memberId,
                                         @SpringQueryMap FindMapByNameReqDto findMapByNameReqVo);

    @GetMapping(value = "/common/list", produces = "application/json")
    ResponseEntity<Object> findAllCommonCode(@SpringQueryMap FindAddableThingsReqDto findAllCommonCodeReq);
}
