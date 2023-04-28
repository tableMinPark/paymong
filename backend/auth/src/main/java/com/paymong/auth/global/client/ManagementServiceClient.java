package com.paymong.auth.global.client;

import com.paymong.auth.global.vo.request.FindMongReqVo;
import com.paymong.auth.global.vo.response.FindMongResVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "MANAGEMENT")
public interface ManagementServiceClient {

    @GetMapping("/management/mong")
    ResponseEntity<FindMongResVo> findMongByMember(FindMongReqVo findMongReqVo);
}
