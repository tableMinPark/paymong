package com.paymong.battle.global.client;

import com.paymong.battle.battle.dto.request.AddPointReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "member")
public interface MemberServiceClient {

    @PostMapping("/member/paypoint/point")
    ResponseEntity<Object> addPoint(@RequestHeader(value = "MemberId") String memberIdStr,
        @RequestBody AddPointReqDto addPointReqDto);

}
