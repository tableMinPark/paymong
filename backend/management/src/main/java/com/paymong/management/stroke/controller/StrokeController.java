package com.paymong.management.stroke.controller;

import com.paymong.management.global.code.ManagementStateCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.response.ErrorResponse;
import com.paymong.management.stroke.service.StrokeService;
import com.paymong.management.stroke.vo.StrokeMongReqVo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management/stroke")
public class StrokeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StrokeController.class);
    private final StrokeService strokeService;
    @Value("${header.mong}")
    String headerMong;
    @PutMapping
    public ResponseEntity<Object> strokeMong(HttpServletRequest httpServletRequest) throws Exception{
        Long mongId = Long.parseLong(httpServletRequest.getHeader(headerMong));

        try {
            if(mongId == null) {
                throw new NullPointerException();
            }
            StrokeMongReqVo strokeMongReqVo = new StrokeMongReqVo(mongId);
            strokeService.strokeMong(strokeMongReqVo);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
        }catch (NullPointerException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NULL_POINT.getCode(), ManagementStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NULL_POINT));
        }catch (NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_FOUND.getCode(), ManagementStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_FOUND));
        }
    }
}
