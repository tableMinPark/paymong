package com.paymong.management.training.controller;

import com.paymong.management.global.code.ManagementStateCode;
import com.paymong.management.global.exception.NotFoundActionException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.exception.UnknownException;
import com.paymong.management.global.response.ErrorResponse;
import com.paymong.management.training.dto.WalkingReqDto;
import com.paymong.management.training.service.TrainingService;
import com.paymong.management.training.vo.WalkingReqVo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management/training")
public class TrainingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingController.class);
    private final TrainingService trainingService;

    @Value("${header.mong}")
    String headerMong;
    @PutMapping
    public ResponseEntity<Object> training(HttpServletRequest httpServletRequest) throws Exception{
        String mongIdStr = httpServletRequest.getHeader(headerMong);
        try {
            if(mongIdStr == null || mongIdStr.equals("")) throw new NullPointerException();
            Long mongId = Long.parseLong(mongIdStr);
            trainingService.training(mongId);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
        }catch (NullPointerException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NULL_POINT.getCode(), ManagementStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NULL_POINT));
        }catch (NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_FOUND.getCode(), ManagementStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_FOUND));
        }catch (NotFoundActionException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_ACTION.getCode(), ManagementStateCode.NOT_ACTION.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_ACTION));
        }catch (UnknownException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.UNKNOWN.getCode(), ManagementStateCode.UNKNOWN.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.UNKNOWN));
        }
    }

    @PutMapping("/walking")
    public ResponseEntity<Object> walking(WalkingReqDto walkingReqDto, HttpServletRequest httpServletRequest) throws Exception{
        String mongIdStr = httpServletRequest.getHeader(headerMong);

        try {
            if(walkingReqDto.getWalkingCount() == null || mongIdStr == null || mongIdStr.equals("")){
                throw new NullPointerException();
            }
            Long mongId = Long.parseLong(mongIdStr);
            WalkingReqVo walkingReqVo = new WalkingReqVo();
            walkingReqVo.setWalkingCount(walkingReqVo.getWalkingCount());
            walkingReqVo.setMongId(mongId);
            trainingService.walking(walkingReqVo);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
        }catch (NullPointerException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NULL_POINT.getCode(), ManagementStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NULL_POINT));
        }catch (NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_FOUND.getCode(), ManagementStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_FOUND));
        }catch (NotFoundActionException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_ACTION.getCode(), ManagementStateCode.NOT_ACTION.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_ACTION));
        }catch (UnknownException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.UNKNOWN.getCode(), ManagementStateCode.UNKNOWN.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.UNKNOWN));
        }
    }
}
