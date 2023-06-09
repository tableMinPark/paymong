package com.paymong.management.poop.controller;

import com.paymong.management.global.code.ManagementStateCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.response.ErrorResponse;
import com.paymong.management.global.scheduler.PoopScheduler;
import com.paymong.management.poop.service.PoopService;
import com.paymong.management.poop.vo.PoopMongReqVo;
import com.paymong.management.status.dto.MongStatusDto;
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
    private final PoopScheduler poopScheduler;

    @Value("${header.mong}")
    String headerMong;
    /* 똥생성은 소켓에서 */

    /* 똥 삭제 PUT */
    @PutMapping
    public ResponseEntity<Object> removePoop(HttpServletRequest httpServletRequest) throws Exception{
        String mongIdStr = httpServletRequest.getHeader(headerMong);
        LOGGER.info("똥을 치웁니다. id : {}", mongIdStr);
        try {
            if(mongIdStr == null || mongIdStr.equals("")) throw new NullPointerException();
            Long mongId = Long.parseLong(mongIdStr);
            PoopMongReqVo poopMongReqVo = new PoopMongReqVo(mongId);
            MongStatusDto mongStatusDto = poopService.removePoop(poopMongReqVo);
            return ResponseEntity.status(HttpStatus.OK).body(mongStatusDto);
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
        poopScheduler.startScheduler(mongId);
        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
    }

    @GetMapping("/stop")
    public ResponseEntity<Object> stopPoop(@RequestParam("mongId") Long mongId){
        poopScheduler.stopScheduler(mongId);
        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
    }
}
