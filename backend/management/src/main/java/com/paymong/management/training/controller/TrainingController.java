package com.paymong.management.training.controller;

import com.paymong.management.global.code.ManagementStateCode;
import com.paymong.management.global.exception.NotFoundActionException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.exception.UnknownException;
import com.paymong.management.global.response.ErrorResponse;
import com.paymong.management.status.dto.MongStatusDto;
import com.paymong.management.training.dto.TrainingReqDto;
import com.paymong.management.training.dto.WalkingReqDto;
import com.paymong.management.training.service.TrainingService;
import com.paymong.management.training.vo.TrainingReqVo;
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
    @Value("${header.member}")
    String headerMember;
    @PutMapping
    public ResponseEntity<Object> training(@RequestBody TrainingReqDto trainingReqDto, HttpServletRequest httpServletRequest) throws Exception{
        String mongIdStr = httpServletRequest.getHeader(headerMong);
        String memberIdStr = httpServletRequest.getHeader(headerMember);
        LOGGER.info("훈련을 시작합니다. id : {}", mongIdStr);
        try {
            if(trainingReqDto.getTrainingCount() == null)throw new NullPointerException();
            if(mongIdStr == null || mongIdStr.equals("")) throw new NullPointerException();
            if(memberIdStr == null || memberIdStr.equals("")) throw new NullPointerException();
            Long mongId = Long.parseLong(mongIdStr);
            Long memberId = Long.parseLong(memberIdStr);

            TrainingReqVo trainingReqVo = new TrainingReqVo();
            trainingReqVo.setWalkingCount(trainingReqDto.getTrainingCount());
            trainingReqVo.setMongId(mongId);
            trainingReqVo.setMemberId(memberId);

            MongStatusDto mongStatusDto = trainingService.training(trainingReqVo);
            return ResponseEntity.status(HttpStatus.OK).body(mongStatusDto);
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
    public ResponseEntity<Object> walking(@RequestBody WalkingReqDto walkingReqDto, HttpServletRequest httpServletRequest) throws Exception{
        String mongIdStr = httpServletRequest.getHeader(headerMong);
        String memberIdStr = httpServletRequest.getHeader(headerMember);
        LOGGER.info("산책을 시작합니다. id : {}", mongIdStr);
        LOGGER.info("count : {} , mongId : {}, memberId : {}", walkingReqDto.getWalkingCount(), mongIdStr, memberIdStr);
        try {
            if(walkingReqDto.getWalkingCount() == null ){
                LOGGER.info("count가 비었음");
                throw new NullPointerException();
            }
            if(mongIdStr == null || mongIdStr.equals("")) {
                LOGGER.info("mongId가 비었음");
                throw new NullPointerException();
            }
            if(memberIdStr == null || memberIdStr.equals("")) {
                LOGGER.info("memberId가 비었음");
                throw new NullPointerException();
            }

            Long mongId = Long.parseLong(mongIdStr);
            Long memberId = Long.parseLong(memberIdStr);

            WalkingReqVo walkingReqVo = new WalkingReqVo();
            walkingReqVo.setWalkingCount(walkingReqDto.getWalkingCount());
            walkingReqVo.setMongId(mongId);
            walkingReqVo.setMemberId(memberId);
            MongStatusDto mongStatusDto = trainingService.walking(walkingReqVo);
            return ResponseEntity.status(HttpStatus.OK).body(mongStatusDto);

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
