package com.paymong.management.poop.controller;

import com.paymong.management.global.code.ManagementStateCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.response.ErrorResponse;
import com.paymong.management.global.scheduler.ManagementScheduler;
import com.paymong.management.global.scheduler.service.SchedulerService;
import com.paymong.management.mong.controller.MongController;
import com.paymong.management.poop.scheduler.PoopScheduler;
import com.paymong.management.poop.service.PoopService;
import com.paymong.management.poop.vo.PoopMongReqVo;
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
@RequestMapping("/management/poop")
public class PoopController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoopController.class);

    private final PoopService poopService;
    private final SchedulerService schedulerService;

    @Value("${header.mong}")
    String headerMong;
    /* 똥생성은 소켓에서 */

    /* 똥 삭제 PUT */
    @PutMapping
    public ResponseEntity<Object> removePoop(HttpServletRequest httpServletRequest) throws Exception{
        Long mongId = Long.parseLong(httpServletRequest.getHeader(headerMong));
        try {
            if(mongId == null) throw new NullPointerException();
            PoopMongReqVo poopMongReqVo = new PoopMongReqVo(mongId);
//            PoopMongReqVo poopMongReqVo = new PoopMongReqVo(1L);
            poopService.removePoop(poopMongReqVo);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
        }catch (NullPointerException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NULL_POINT.getCode(), ManagementStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NULL_POINT));
        }catch (NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_FOUND.getCode(), ManagementStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_FOUND));
        }
    }

    @GetMapping("/start")
    public ResponseEntity<Object> startPoop(@RequestParam("mongId") Long mongId){
        schedulerService.startOf(0,mongId);
        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
    }

    @GetMapping("/stop")
    public ResponseEntity<Object> stopPoop(@RequestParam("mongId") Long mongId){
        schedulerService.stopOf(0, mongId);
        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
    }
}
