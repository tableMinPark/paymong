package com.paymong.member.paypoint.controller;

import com.paymong.member.global.code.MymapStateCode;
import com.paymong.member.global.code.PaypointStateCode;
import com.paymong.member.global.exception.NotFoundAuthException;
import com.paymong.member.global.exception.NotFoundMapCodeException;
import com.paymong.member.global.exception.NotFoundMapException;
import com.paymong.member.global.exception.NotFoundMymapException;
import com.paymong.member.global.response.ErrorResponse;
import com.paymong.member.paypoint.dto.request.AddPaypointReqDto;
import com.paymong.member.paypoint.dto.request.FindTotalPayReqDto;
import com.paymong.member.paypoint.dto.response.AddPaypointResDto;
import com.paymong.member.paypoint.dto.response.AddPointReqDto;
import com.paymong.member.paypoint.dto.response.FindTotalPayResDto;
import com.paymong.member.paypoint.entity.PointHistory;
import com.paymong.member.paypoint.service.PaypointService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/paypoint")
public class PaypointController {
    final public PaypointService paypointService;
    @PostMapping("")
    public ResponseEntity<Object> addPaypoint(@RequestHeader(value = "MemberId") String memberIdStr,
                                              @RequestBody AddPaypointReqDto addPaypointReqDto
                                              ){
        log.info("addPay - Call");
        try {
            AddPaypointResDto addPayResDto = paypointService.addPaypoint(memberIdStr, addPaypointReqDto);
            return ResponseEntity.status(HttpStatus.OK).body(addPayResDto);
        }catch (NotFoundAuthException e){
            log.info("code : {}, message : {}", PaypointStateCode.FAIL_AUTH.getCode(), PaypointStateCode.FAIL_AUTH.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(PaypointStateCode.FAIL_AUTH));
        }catch (NotFoundMapException e){
            log.info("code : {}, message : {}", PaypointStateCode.FAIL_COMMON.getCode(), PaypointStateCode.FAIL_COMMON.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(PaypointStateCode.FAIL_COMMON));
        }catch (NotFoundMapCodeException e){
            log.info("code : {}, message : {}", PaypointStateCode.FAIL_COLLECT.getCode(), PaypointStateCode.FAIL_COLLECT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(PaypointStateCode.FAIL_COLLECT));
        }catch (NotFoundMymapException e){
            log.info("code : {}, message : {}", MymapStateCode.MYMAP_ERROR.getCode(), MymapStateCode.MYMAP_ERROR.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(MymapStateCode.MYMAP_ERROR));
        }
        catch (Exception e){
            log.info("code : {}, message : {}", PaypointStateCode.UNKNOWN.getCode(), PaypointStateCode.UNKNOWN.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }
    }

    @PostMapping("/point")
    public ResponseEntity<Object> addPoint(@RequestHeader(value = "MemberId") String memberIdStr,
                                            @RequestBody AddPointReqDto addPointReqDto){
        log.info("addPoint - Call");
        try {
            PointHistory paypoint =  paypointService.addPoint(memberIdStr, addPointReqDto);
            return ResponseEntity.status(HttpStatus.OK).body(paypoint);
        }catch (Exception e){
            System.out.println(e);
            log.info("code : {}, message : {}", PaypointStateCode.UNKNOWN.getCode(), PaypointStateCode.UNKNOWN.name());
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
            log.info("code : {}, message : {}", PaypointStateCode.UNKNOWN.getCode(), PaypointStateCode.UNKNOWN.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }
    }

    @GetMapping("/total")
    public ResponseEntity<Object> findTotalPay(@RequestHeader(value = "MemberId") String memberIdStr,
                                               FindTotalPayReqDto findTotalPayReqDto){
        log.info("findTotalPay - Call");
        try {
            FindTotalPayResDto findTotalPayResDto = paypointService.findTotalPay(memberIdStr, findTotalPayReqDto);
            return ResponseEntity.status(HttpStatus.OK).body(findTotalPayResDto);
        }catch (Exception e){
            System.out.println(e);
            log.info("code : {}, message : {}", PaypointStateCode.UNKNOWN.getCode(), PaypointStateCode.UNKNOWN.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }

    }
}
