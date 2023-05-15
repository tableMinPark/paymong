package com.paymong.management.mong.controller;

import com.paymong.management.global.code.ManagementStateCode;
import com.paymong.management.global.exception.*;
import com.paymong.management.global.redis.RedisService;
import com.paymong.management.global.response.ErrorResponse;
import com.paymong.management.global.scheduler.DeathScheduler;
import com.paymong.management.global.scheduler.service.SchedulerService;
import com.paymong.management.mong.dto.*;
import com.paymong.management.mong.service.MongService;
import com.paymong.management.mong.vo.AddMongReqVo;
import com.paymong.management.mong.vo.AddMongResVo;
import com.paymong.management.mong.vo.FindMongReqVo;
import com.paymong.management.mong.vo.FindMongResVo;
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
@RequestMapping("/management")
public class MongController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongController.class);
    private final MongService mongService;
    private final DeathScheduler deathScheduler;
    private final RedisService redisService;

    @Value("${header.member}")
    String headerMember;

    @Value("${header.mong}")
    String headerMong;
    /* 몽 생성 */
    @PostMapping
    public ResponseEntity<Object> addMong(@RequestBody AddMongReqDto addMongReqDto, HttpServletRequest httpServletRequest) throws Exception{
        try{
            if(addMongReqDto.getName() == null){
                throw new NullPointerException();
            }
            String memberIdStr = httpServletRequest.getHeader(headerMember);

            if(memberIdStr == null || memberIdStr.equals("")) throw new NullPointerException();

            Long memberId = Long.parseLong(memberIdStr);

            LOGGER.info("새로운 몽을 추가합니다. id : {}", memberId);
            AddMongReqVo addMongReqVo = new AddMongReqVo(addMongReqDto);
            addMongReqVo.setMemberId(memberId);
            AddMongResVo addMongResVo = mongService.addMong(addMongReqVo);
            mongService.startScheduler(addMongResVo.getMongId());
            AddMongResDto addMongResDto = new AddMongResDto(addMongResVo);
            return ResponseEntity.status(HttpStatus.OK).body(addMongResDto);
        }catch (NullPointerException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NULL_POINT.getCode(), ManagementStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NULL_POINT));
        }catch(NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_FOUND.getCode(), ManagementStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_FOUND));
        }catch (AlreadyExistMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.ALREADY_EXIST.getCode(), ManagementStateCode.ALREADY_EXIST.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.ALREADY_EXIST));
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
        try {
            mongService.startScheduler(mongId);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
        }catch (NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_FOUND.getCode(), ManagementStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_FOUND));
        }
    }

//    @GetMapping("/death/start")
//    public ResponseEntity<Object> startDeath(@RequestParam("mongId") Long mongId){
//        deathScheduler.startScheduler(mongId);
//        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
//    }
//
//    @GetMapping("/stop")
//    public ResponseEntity<Object> stopDeath(@RequestParam("mongId") Long mongId){
//        deathScheduler.stopScheduler(mongId);
//        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
//    }
//
//    @GetMapping("/death")
//    public ResponseEntity<Object> deathCountMong(HttpServletRequest httpServletRequest){
//        Long mongId = Long.parseLong(httpServletRequest.getHeader("MongId"));
//        deathScheduler.startScheduler(mongId);
//        return ResponseEntity.ok().body(new ErrorResponse(ManagementStateCode.SUCCESS));
//    }
//
//    @GetMapping("/pause")
//    public ResponseEntity<Object> deathPauseMong(HttpServletRequest httpServletRequest){
//        Long mongId = Long.parseLong(httpServletRequest.getHeader("MongId"));
//        deathScheduler.pauseScheduler(mongId);
//        return ResponseEntity.ok().body(new ErrorResponse(ManagementStateCode.SUCCESS));
//    }
//
//    @GetMapping("/restart")
//    public ResponseEntity<Object> deathRestartMong(HttpServletRequest httpServletRequest){
//        Long mongId = Long.parseLong(httpServletRequest.getHeader("MongId"));
//        deathScheduler.restartScheduler(mongId);
//        return ResponseEntity.ok().body(new ErrorResponse(ManagementStateCode.SUCCESS));
//    }
//
//    @GetMapping("/redis/add")
//    public ResponseEntity<Object> redisTestAdd(){
//
//        deathScheduler.addRedis();
//        return ResponseEntity.ok().body(new ErrorResponse(ManagementStateCode.SUCCESS));
//    }
//
//    @GetMapping("/redis/out")
//    public ResponseEntity<Object> redisTestOut(){
//
//        redisService.getRedisMong("death").stream().forEach(deathScheduler::restartScheduler);
//        return ResponseEntity.ok().body(new ErrorResponse(ManagementStateCode.SUCCESS));
//    }

    @PutMapping("/evolution")
    public ResponseEntity<Object> evolutionMong(HttpServletRequest httpServletRequest){
        String mongIdStr = httpServletRequest.getHeader(headerMong);
        LOGGER.info("진화를 시작합니다. id : {}", mongIdStr);
        try {
            if(mongIdStr == null || mongIdStr.equals("")) throw new NullPointerException();
            Long mongId = Long.parseLong(mongIdStr);
            mongService.evolutionMong(mongId);
            EvolutionMongResDto evolutionMongResDto = mongService.mongSleepCheck(mongId);
            return  ResponseEntity.ok().body(evolutionMongResDto);
        }catch (NullPointerException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NULL_POINT.getCode(), ManagementStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NULL_POINT));
        }catch (NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_FOUND.getCode(), ManagementStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_FOUND));
        }catch (GatewayException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.GATEWAY_ERROR.getCode(), ManagementStateCode.GATEWAY_ERROR.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.GATEWAY_ERROR));
        }catch (UnsuitableException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.UNSUITABLE.getCode(), ManagementStateCode.UNSUITABLE.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.UNSUITABLE));
        }
    }

    @PutMapping("/graduation")
    public ResponseEntity<Object> graduationMong(HttpServletRequest httpServletRequest){
        String mongIdStr = httpServletRequest.getHeader(headerMong);
        LOGGER.info("졸업을 시작합니다. id : {}", mongIdStr);
        try {
            if(mongIdStr == null || mongIdStr.equals("")) throw new NullPointerException();
            Long mongId = Long.parseLong(mongIdStr);
            GraduationMongResDto graduationMong = mongService.graduationMong(mongId);
            return  ResponseEntity.ok().body(graduationMong);
        }catch (NullPointerException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NULL_POINT.getCode(), ManagementStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NULL_POINT));
        }catch (NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_FOUND.getCode(), ManagementStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_FOUND));
        }catch (UnsuitableException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.UNSUITABLE.getCode(), ManagementStateCode.UNSUITABLE.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.UNSUITABLE));
        }
    }

    @PostMapping("/map")
    public ResponseEntity<Object> changeMap(@RequestBody MapCodeDto mapCodeDto, HttpServletRequest httpServletRequest){

        String memberIdStr = httpServletRequest.getHeader(headerMember);
        LOGGER.info("맵이 바뀝니도. memberId = {}", memberIdStr);
        try {
            if(memberIdStr == null || memberIdStr.equals("")) throw new NullPointerException();
            if(mapCodeDto.getMapCode() == null || mapCodeDto.getMapCode().equals("")) throw new NullPointerException();
            Long memberId = Long.parseLong(memberIdStr);
            MapCodeWsDto mapCodeWsDto = MapCodeWsDto.builder()
                    .memberId(memberId)
                    .mapCode(mapCodeDto.getMapCode())
                    .build();
            mongService.changeMap(mapCodeWsDto);
            return ResponseEntity.ok().body(new ErrorResponse(ManagementStateCode.SUCCESS));
        }catch (NullPointerException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NULL_POINT.getCode(), ManagementStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NULL_POINT));
        }


    }
}
