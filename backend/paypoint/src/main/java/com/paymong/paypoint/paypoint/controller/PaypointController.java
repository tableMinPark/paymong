package com.paymong.paypoint.paypoint.controller;

import com.paymong.paypoint.global.code.PaypointStateCode;
import com.paymong.paypoint.global.response.ErrorResponse;
import com.paymong.paypoint.paypoint.dto.AddPayReqDto;
import com.paymong.paypoint.paypoint.dto.AddPointReqDto;
import com.paymong.paypoint.paypoint.entity.PointHistory;
import com.paymong.paypoint.paypoint.service.PaypointService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/paypoint")
public class PaypointController {
    final public PaypointService paypointService;
    @PostMapping("")
    public ResponseEntity<Object> addPay(@RequestHeader(value = "MemberId") String memberIdStr,
                                              @RequestHeader(value = "MongId") String mongIdStr,
                                              @RequestBody AddPayReqDto addPaypointReqDto
                                              ){
        log.info("addPay - Call");
        try {
            paypointService.addPay(memberIdStr, mongIdStr, addPaypointReqDto);
            Map <String,String> testRet = new HashMap<>();
            testRet.put("key","짱짱걸");
            return ResponseEntity.status(HttpStatus.OK).body(testRet);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }
    }

    @PostMapping("/point")
    public ResponseEntity<Object> addPoint(@RequestHeader(value = "MemberId") String memberIdStr,
                                           @RequestHeader(value = "MongId") String mongIdStr,
                                            @RequestBody AddPointReqDto addPointReqDto){
        log.info("addPoint - Call");
        try {
            paypointService.addPoint(memberIdStr, mongIdStr, addPointReqDto);
            return ResponseEntity.status(HttpStatus.OK).body("");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }
    }


    @GetMapping("/list")
    public ResponseEntity<Object> findAllPaypoint(@RequestHeader(value = "MemberId") String memberIdStr){
        log.info("findAllPaypoint - Call");
        try {
            List<PointHistory> ret= paypointService.findAllPaypoint(memberIdStr);
            return ResponseEntity.status(HttpStatus.OK).body(ret);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }
    }

}
