package com.paymong.member.global.client;


import com.paymong.member.member.vo.FindMongReqVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "management")
public interface ManagementServiceClient {

    @GetMapping("/management/mong")
    ResponseEntity<Object> findMongByMember(@SpringQueryMap FindMongReqVo findMongReqVo);

    @PutMapping("/management/poop")
    ResponseEntity<Object> clearPoop(@RequestHeader("MongId") String memberId);

}
