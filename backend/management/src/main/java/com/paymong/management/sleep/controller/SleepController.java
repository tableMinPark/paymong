package com.paymong.management.sleep.controller;

import com.paymong.management.global.code.ManagementStateCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.response.ErrorResponse;
import com.paymong.management.sleep.service.SleepService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management/sleep")
public class SleepController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SleepController.class);

    private final SleepService sleepService;

    @Value("MongId")
    String headerMong;

    @PutMapping("/toggle")
    public ResponseEntity<Object> sleepMong(HttpServletRequest httpServletRequest) throws Exception{

        String mongIdStr = httpServletRequest.getHeader(headerMong);
        try {
            if(mongIdStr == null || mongIdStr.equals("")) throw new NullPointerException();
            Long mongId = Long.parseLong(mongIdStr);
            sleepService.sleepMong(mongId);
            return ResponseEntity.ok().body(new ErrorResponse(ManagementStateCode.SUCCESS));
        }catch (NullPointerException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NULL_POINT.getCode(), ManagementStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NULL_POINT));
        }catch (NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_FOUND.getCode(), ManagementStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_FOUND));
        }
    }



}
