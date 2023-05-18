package com.paymong.member.global.client;


import com.paymong.member.member.vo.FindMongReqVo;
import com.paymong.member.paypoint.dto.request.SendMapReqDto;
import com.paymong.member.paypoint.dto.request.SendPointReqDto;
import com.paymong.member.things.dto.request.SendThingsReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "management")
public interface ManagementServiceClient {

    @GetMapping("/management/mong")
    ResponseEntity<Object> findMongByMember(@SpringQueryMap FindMongReqVo findMongReqVo);

    @PutMapping("/management/poop")
    ResponseEntity<Object> clearPoop(@RequestHeader("MongId") String mongId);

    @PostMapping("/management/map")
    ResponseEntity<Object> sendMap(@RequestHeader("MemberId") String memberId,
                                   @RequestBody SendMapReqDto sendMapReqDto);
    @PostMapping("/management/things")
    ResponseEntity<Object> sendThings(@RequestHeader("MemberId") String memberId,
                                   @RequestBody SendThingsReqDto sendThingsReqDto);

    @PostMapping("/management/point")
    ResponseEntity<Object> sendPoint(@RequestHeader("MemberId") String memberId,
                                      @RequestBody SendPointReqDto sendPointReqDto);
}
