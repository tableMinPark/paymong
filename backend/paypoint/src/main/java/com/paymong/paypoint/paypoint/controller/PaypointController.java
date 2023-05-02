package com.paymong.paypoint.paypoint.controller;

import com.paymong.paypoint.global.code.PaypointStateCode;
import com.paymong.paypoint.global.response.ErrorResponse;
import com.paymong.paypoint.paypoint.dto.AddPaypointReqDto;
import com.paymong.paypoint.paypoint.service.PaypointService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/paypoint")
public class PaypointController {
    final public PaypointService paypointService;
    @PostMapping("")
    public ResponseEntity<Object> addPay(@RequestHeader(value = "MemberKey") String memberKey,
                                              @RequestHeader(value = "MongKey") String mongKey,
                                              @RequestBody AddPaypointReqDto addPaypointReqDto
                                              ){
        log.info("addPay - Call");
        try {
            paypointService.addPay(memberKey, mongKey, addPaypointReqDto);
            return ResponseEntity.status(HttpStatus.OK).body("");
        }catch (NumberFormatException e){
            System.out.println("NumberFormatException");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }
    }

    @PostMapping("/point")
    public ResponseEntity<Object> addPoint(){
        log.info("addPoint - Call");
        try {

            return ResponseEntity.status(HttpStatus.OK).body("");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }
    }


    @GetMapping("/list")
    public ResponseEntity<Object> findAllPaypoint(@RequestHeader(value = "MemberKey") String memberKey){
        log.info("findAllPaypoint - Call");
        try {
            paypointService.findAllPaypoint(memberKey);
        }catch (Exception e){

        }
        return null;
    }

}
