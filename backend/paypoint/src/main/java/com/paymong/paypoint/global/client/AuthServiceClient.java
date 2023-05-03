package com.paymong.paypoint.global.client;

import com.paymong.paypoint.paypoint.dto.ModifyPaypointReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth")
public interface AuthServiceClient {
    @PutMapping(value = "/auth/paypoint", produces = "application/json")
    ResponseEntity<Object>  modifyPaypoint(@RequestHeader("MemberId") String memberId,
                                           @RequestHeader("MongId") String mongId,
                                           @RequestBody ModifyPaypointReqDto modifyPaypointReqDto
                                           );

}
