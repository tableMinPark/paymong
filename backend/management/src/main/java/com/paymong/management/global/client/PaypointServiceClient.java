package com.paymong.management.global.client;

import com.paymong.management.global.dto.AddPayDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("paypoint")
public interface PaypointServiceClient {
    @PostMapping("/paypoint")
    public ResponseEntity<Object> addaddPay(@RequestHeader("MemberId") String memberId,
                                         @RequestHeader("MongId") String mongId,
                                         @RequestBody AddPayDto addPayDto
                                         );
}
