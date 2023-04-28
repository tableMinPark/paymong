package com.paymong.management.mong.controller;

import com.paymong.management.global.code.ErrorCode;
import com.paymong.management.global.dto.ErrorDto;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.mong.dto.AddMongReqDto;
import com.paymong.management.mong.dto.AddMongResDto;
import com.paymong.management.mong.service.MongService;
import com.paymong.management.mong.vo.AddMongReqVo;
import com.paymong.management.mong.vo.AddMongResVo;
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
            LOGGER.info("code : {}, message : {}", ErrorCode.NULL_POINT.getCode(), ErrorCode.NULL_POINT.name());
            ErrorDto errorDto = new ErrorDto(ErrorCode.NULL_POINT);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
        }catch(NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ErrorCode.NOT_FOUND.getCode(), ErrorCode.NOT_FOUND.name());
            ErrorDto errorDto = new ErrorDto(ErrorCode.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
        }

    }
}
