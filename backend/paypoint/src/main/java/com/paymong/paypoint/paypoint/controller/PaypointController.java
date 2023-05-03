package com.paymong.paypoint.paypoint.controller;

import com.paymong.paypoint.global.code.PaypointStateCode;
import com.paymong.paypoint.global.exception.InvalidIdException;
import com.paymong.paypoint.global.exception.NotFoundAuthException;
import com.paymong.paypoint.global.exception.NotFoundMapCodeException;
import com.paymong.paypoint.global.exception.NotFoundMapException;
import com.paymong.paypoint.global.response.ErrorResponse;
import com.paymong.paypoint.paypoint.dto.AddPayReqDto;
import com.paymong.paypoint.paypoint.dto.AddPayResDto;
import com.paymong.paypoint.paypoint.dto.AddPointReqDto;
import com.paymong.paypoint.paypoint.entity.PointHistory;
import com.paymong.paypoint.paypoint.service.PaypointService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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
            AddPayResDto addPayResDto = paypointService.addPay(memberIdStr, mongIdStr, addPaypointReqDto);
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
        }catch (Exception e){
            log.info("code : {}, message : {}", PaypointStateCode.UNKNOWN.getCode(), PaypointStateCode.UNKNOWN.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }
    }

    @PostMapping("/point")
    public ResponseEntity<Object> addPoint(@RequestHeader(value = "MemberId") String memberIdStr,
                                           @RequestHeader(value = "MongId") String mongIdStr,
                                            @RequestBody AddPointReqDto addPointReqDto){
        log.info("addPoint - Call");
        try {
            PointHistory paypoint =  paypointService.addPoint(memberIdStr, mongIdStr, addPointReqDto);
            return ResponseEntity.status(HttpStatus.OK).body(paypoint);
        }catch (InvalidIdException e){
            log.info("code : {}, message : {}", PaypointStateCode.NOTEXIST.getCode(), PaypointStateCode.NOTEXIST.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(PaypointStateCode.NOTEXIST));
        }
        catch (Exception e){
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

}
