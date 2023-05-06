package com.paymong.management.global.client;

import com.paymong.management.global.dto.AddPointDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("member")
public interface MemberServiceClient {

    @PostMapping("/member/point")
    public ResponseEntity<Object> addPoint(@RequestHeader(value = "MemberId") String memberIdStr,
                                              @RequestBody AddPointDto addPointDto);
}
