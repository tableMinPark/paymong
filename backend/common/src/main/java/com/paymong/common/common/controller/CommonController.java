package com.paymong.common.common.controller;

import com.paymong.common.common.dto.request.FindAllCommonCodeReqDto;
import com.paymong.common.common.dto.request.FindCodeByNameReqDto;
import com.paymong.common.common.dto.request.FindCommonCodeReqDto;
import com.paymong.common.common.dto.response.CommonCodeDto;
import com.paymong.common.common.dto.response.FindAllCommonCodeResDto;
import com.paymong.common.common.dto.response.FindAllFoodResDto;
import com.paymong.common.common.service.CommonService;
import com.paymong.common.global.code.ErrorStateCode;
import com.paymong.common.global.exception.NotFoundException;
import com.paymong.common.global.response.ErrorResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/common")
public class CommonController {

    private final CommonService commonService;

    @GetMapping("/list")
    public ResponseEntity<Object> findAllCommonCode(
        FindAllCommonCodeReqDto findAllCommonCodeReqDto) {
        log.info("findAllCommonCode - Call");
        try {
            List<CommonCodeDto> commonCodeDtoList = commonService.findAllCommonCode(
                findAllCommonCodeReqDto);
            return ResponseEntity.ok().body(new FindAllCommonCodeResDto(commonCodeDtoList));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(ErrorStateCode.NOTFOUND_GROUPCODE));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<Object> findCommonCode(FindCommonCodeReqDto findCommonCodeReqDto) {
        log.info("findCommonCode - Call");
        try {
            CommonCodeDto commonCodeDto = commonService.findCommonCode(findCommonCodeReqDto);
            return ResponseEntity.ok().body(commonCodeDto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }

    @GetMapping("/egg")
    public ResponseEntity<Object> findRandomEgg() {
        log.info("findRandomEgg - Call");
        try {
            CommonCodeDto commonCodeDto = commonService.findRandomEgg();
            return ResponseEntity.ok().body(commonCodeDto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }

    @GetMapping("/food/{foodCategory}")
    public ResponseEntity<Object> findAllFood(@RequestHeader(value = "Mongkey") String mongKey,
        @PathVariable("foodCategory") String foodCategory) {
        log.info("findAllFood - Call");
        log.info("foodCategory - {}", foodCategory);
        try {
            FindAllFoodResDto findAllFoodResDto = commonService.findAllFood(foodCategory, mongKey);
            return ResponseEntity.ok().body(findAllFoodResDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(ErrorStateCode.NOTFOUND_FOODCODE));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }

    @GetMapping("/name")
    public ResponseEntity<Object> findCodeByName(FindCodeByNameReqDto findCodeByNameReqDto){
        log.info("findCodeByName - Call");
        try{
            CommonCodeDto commonCodeDto = commonService.findCodeByName(findCodeByNameReqDto.getName());
            return ResponseEntity.ok().body(commonCodeDto);
        }catch (NotFoundException e){
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(ErrorStateCode.NOTFOUND_COMMONCODE));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }

}
