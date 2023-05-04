package com.paymong.information.information.controller;

import com.paymong.information.global.code.InformationStateCode;
import com.paymong.information.global.exception.NotFoundMongException;
import com.paymong.information.global.response.ErrorResponse;
import com.paymong.information.information.dto.*;
import com.paymong.information.information.service.MongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/information")
public class InformationController {

    private final MongService mongService;

    @Value("${header.mong}")
    String headerMong;

    @GetMapping("/mong")
    public ResponseEntity<Object> findMong(HttpServletRequest httpServletRequest){
        String mongIdStr = httpServletRequest.getHeader(headerMong);

        try{
            // 아직 map코드는 안받음
            if(mongIdStr == null || mongIdStr.equals("")) throw new NullPointerException();
            Long mongId = Long.parseLong(mongIdStr);
            FindMongDto findMongDto = mongService.findMong(mongId);
            return ResponseEntity.ok().body(findMongDto);
        }catch (NullPointerException e){
            log.info("code : {}, message : {}", InformationStateCode.NULL_POINT.getCode(), InformationStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(InformationStateCode.NULL_POINT));
        }catch (NotFoundMongException e){
            log.info("code : {}, message : {}", InformationStateCode.NOT_FOUND.getCode(), InformationStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(InformationStateCode.NOT_FOUND));
        }
    }

    @GetMapping("/mong/info")
    public ResponseEntity<Object> findMongInfo(HttpServletRequest httpServletRequest){
        String mongIdStr = httpServletRequest.getHeader(headerMong);

        try{
            if(mongIdStr == null || mongIdStr.equals("")) throw new NullPointerException();
            Long mongId = Long.parseLong(mongIdStr);
            FindMongInfoDto findMongInfoDto = mongService.findMongInfo(mongId);
            return ResponseEntity.ok().body(findMongInfoDto);
        }catch (NullPointerException e){
            log.info("code : {}, message : {}", InformationStateCode.NULL_POINT.getCode(), InformationStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(InformationStateCode.NULL_POINT));
        }catch (NotFoundMongException e){
            log.info("code : {}, message : {}", InformationStateCode.NOT_FOUND.getCode(), InformationStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(InformationStateCode.NOT_FOUND));
        }
    }

    @GetMapping("/mong/status")
    public ResponseEntity<Object> findMongStatus(HttpServletRequest httpServletRequest){
        String mongIdStr = httpServletRequest.getHeader(headerMong);
        try{
            if(mongIdStr == null || mongIdStr.equals("")) throw new NullPointerException();
            Long mongId = Long.parseLong(mongIdStr);
            FindMongStatusDto findMongStatusDto = mongService.findMongStatus(mongId);
            return ResponseEntity.ok().body(findMongStatusDto);
        }catch (NullPointerException e){
            log.info("code : {}, message : {}", InformationStateCode.NULL_POINT.getCode(), InformationStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(InformationStateCode.NULL_POINT));
        }catch (NotFoundMongException e){
            log.info("code : {}, message : {}", InformationStateCode.NOT_FOUND.getCode(), InformationStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(InformationStateCode.NOT_FOUND));
        }
    }

    @GetMapping("/mong/status_battle")
    public ResponseEntity<Object> findMongBattle(MongBattleDto mongBattleDto){
        try{
            if(mongBattleDto.getMongId() == null) throw new NullPointerException();;
            FindMongBattleDto findMongBattleDto = mongService.findMongBattle(mongBattleDto.getMongId());
            return ResponseEntity.ok().body(findMongBattleDto);
        }catch (NullPointerException e){
            log.info("code : {}, message : {}", InformationStateCode.NULL_POINT.getCode(), InformationStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(InformationStateCode.NULL_POINT));
        }catch (NotFoundMongException e){
            log.info("code : {}, message : {}", InformationStateCode.NOT_FOUND.getCode(), InformationStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(InformationStateCode.NOT_FOUND));
        }
    }
}
