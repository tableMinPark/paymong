package com.paymong.management.global.client;

import com.paymong.management.global.dto.AddPointDto;
import com.paymong.management.global.dto.FindTotalPayDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("member")
public interface MemberServiceClient {

    @PostMapping("/member/paypoint/point")
    public ResponseEntity<Object> addPoint(@RequestHeader(value = "MemberId") String memberIdStr,
                                              @RequestBody AddPointDto addPointDto);

    @GetMapping("/member/paypoint/total")
    public ResponseEntity<Object> findTotalPay(@RequestHeader(value = "MemberId") String memberIdStr,
                                               @SpringQueryMap FindTotalPayDto findTotalPayDto);
}
