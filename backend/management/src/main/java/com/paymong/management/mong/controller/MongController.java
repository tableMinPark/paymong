package com.paymong.management.mong.controller;

import com.paymong.management.global.code.ManagementStateCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.response.ErrorResponse;
import com.paymong.management.global.scheduler.service.SchedulerService;
import com.paymong.management.mong.dto.AddMongReqDto;
import com.paymong.management.mong.dto.AddMongResDto;
import com.paymong.management.mong.dto.FindMongReqDto;
import com.paymong.management.mong.dto.FindMongResDto;
import com.paymong.management.mong.scheduler.DeathScheduler;
import com.paymong.management.mong.scheduler.HealthScheduler;
import com.paymong.management.mong.service.DeathService;
import com.paymong.management.mong.service.MongService;
import com.paymong.management.mong.vo.AddMongReqVo;
import com.paymong.management.mong.vo.AddMongResVo;
import com.paymong.management.mong.vo.FindMongReqVo;
import com.paymong.management.mong.vo.FindMongResVo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management")
public class MongController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongController.class);
    private final MongService mongService;
    private final SchedulerService schedulerService;

    /* 몽 생성 */
    @PostMapping
    public ResponseEntity<Object> addMong(@RequestBody AddMongReqDto addMongReqDto) throws Exception{
        try{
            LOGGER.info("name : {}", addMongReqDto.getName());
            if(addMongReqDto.getName() == null){
                throw new NullPointerException();
            }
            AddMongReqVo addMongReqVo = new AddMongReqVo(addMongReqDto);
            AddMongResVo addMongResVo = mongService.addMong(addMongReqVo);
            AddMongResDto addMongResDto = new AddMongResDto(addMongResVo);
            return ResponseEntity.status(HttpStatus.OK).body(addMongResDto);
        }catch (NullPointerException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NULL_POINT.getCode(), ManagementStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NULL_POINT));
        }catch(NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_FOUND.getCode(), ManagementStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_FOUND));
        }

    }

    @GetMapping("/mong")
    public ResponseEntity<Object> findMongByMember(FindMongReqDto findMongReqDto) throws Exception{
        try {
            if(findMongReqDto.getMemberId() == null){
                throw new NullPointerException();
            }
            FindMongReqVo findMongReqVo = new FindMongReqVo(findMongReqDto);
            FindMongResVo findMongResVo = mongService.findMongByMember(findMongReqVo);
            FindMongResDto findMongResDto = new FindMongResDto(findMongResVo);
            return ResponseEntity.status(HttpStatus.OK).body(findMongResDto);
        }catch (NullPointerException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NULL_POINT.getCode(), ManagementStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NULL_POINT));
        }catch(NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_FOUND.getCode(), ManagementStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_FOUND));
        }
    }

    @GetMapping("/start")
    public ResponseEntity<Object> startReduceHealth(@RequestParam("mongId") Long mongId){
        schedulerService.startOf(1, mongId);
        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
    }

    @GetMapping("/stop")
    public ResponseEntity<Object> stopReduceHealth(@RequestParam("mongId") Long mongId){
        schedulerService.stopOf(1, mongId);
        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
    }

    @GetMapping("/death/stop")
    public ResponseEntity<Object> deathMong(@RequestParam("mongId") Long mongId){
        schedulerService.stopOf(3, mongId);
        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
    }
}
