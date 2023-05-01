package com.paymong.management.training.controller;

import com.paymong.management.global.code.ManagementStateCode;
import com.paymong.management.global.exception.NotFoundActionException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.response.ErrorResponse;
import com.paymong.management.training.dto.WalkingReqDto;
import com.paymong.management.training.service.TrainingService;
import com.paymong.management.training.vo.WalkingReqVo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management/training")
public class TrainingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingController.class);
    private final TrainingService trainingService;
    @PostMapping
    public ResponseEntity<Object> training() throws Exception{
        try {
            trainingService.training();
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
        }catch (NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_FOUND.getCode(), ManagementStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_FOUND));
        }catch (NotFoundActionException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_ACTION.getCode(), ManagementStateCode.NOT_ACTION.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_ACTION));
        }
    }

    @PostMapping("/walking")
    public ResponseEntity<Object> walking(@RequestBody WalkingReqDto walkingReqDto) throws Exception{
        try {
            if(walkingReqDto.getWalkingCount() == null){
                throw new NullPointerException();
            }
            WalkingReqVo walkingReqVo = new WalkingReqVo(walkingReqDto.getWalkingCount());
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
        }
    }
}
