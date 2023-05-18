package com.paymong.common.common.controller;

import com.paymong.common.common.dto.request.FindAllCommonCodeReqDto;
import com.paymong.common.common.dto.request.FindCodeByNameReqDto;
import com.paymong.common.common.dto.request.FindCommonCodeReqDto;
import com.paymong.common.common.dto.request.FindRandomMongReqDto;
import com.paymong.common.common.dto.response.CommonCodeDto;
import com.paymong.common.common.dto.response.FindAllCommonCodeResDto;
import com.paymong.common.common.dto.response.Food;
import com.paymong.common.common.service.CommonService;
import com.paymong.common.global.code.CommonStateCode;
import com.paymong.common.global.exception.InformationException;
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
            log.info("code : {}, message : {}", CommonStateCode.SUCCESS.getCode(),
                CommonStateCode.SUCCESS.name());
            return ResponseEntity.ok().body(new FindAllCommonCodeResDto(commonCodeDtoList));
        } catch (NotFoundException e) {
            log.info("code : {}, message : {}", CommonStateCode.NOTFOUND_GROUPCODE.getCode(),
                CommonStateCode.NOTFOUND_GROUPCODE.name());
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(CommonStateCode.NOTFOUND_GROUPCODE));
        } catch (RuntimeException e) {
            log.info("code : {}, message : {}", CommonStateCode.RUNTIME.getCode(),
                CommonStateCode.RUNTIME.name());
            return ResponseEntity.badRequest().body(new ErrorResponse(CommonStateCode.RUNTIME));
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<Object> findCommonCode(FindCommonCodeReqDto findCommonCodeReqDto) {
        log.info("findCommonCode - Call");
        try {
            log.info("code : {}, message : {}", CommonStateCode.SUCCESS.getCode(),
                CommonStateCode.SUCCESS.name());
            CommonCodeDto commonCodeDto = commonService.findCommonCode(findCommonCodeReqDto);
            return ResponseEntity.ok().body(commonCodeDto);
        } catch (RuntimeException e) {
            log.info("code : {}, message : {}", CommonStateCode.RUNTIME.getCode(),
                CommonStateCode.RUNTIME.name());
            return ResponseEntity.badRequest().body(new ErrorResponse(CommonStateCode.RUNTIME));
        }
    }

    @GetMapping("/food/{foodCategory}")
    public ResponseEntity<Object> findAllFood(@RequestHeader(value = "MongId") String mongId,
        @PathVariable("foodCategory") String foodCategory) {
        log.info("findAllFood - Call");
        log.info("foodCategory - {}", foodCategory);
        try {
            List<Food> findAllFoodResDto = commonService.findAllFood(foodCategory, mongId);
            log.info("code : {}, message : {}", CommonStateCode.SUCCESS.getCode(),
                CommonStateCode.SUCCESS.name());
            return ResponseEntity.ok().body(findAllFoodResDto);
        } catch (NotFoundException e) {
            log.info("code : {}, message : {}", CommonStateCode.NOTFOUND_FOODCODE.getCode(),
                CommonStateCode.NOTFOUND_FOODCODE.name());
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(CommonStateCode.NOTFOUND_FOODCODE));
        } catch (InformationException e) {
            log.info("code : {}, message : {}", CommonStateCode.INFORMATION.getCode(),
                CommonStateCode.INFORMATION.name());
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(CommonStateCode.INFORMATION));
        } catch (RuntimeException e) {
            log.info("code : {}, message : {}", CommonStateCode.RUNTIME.getCode(),
                CommonStateCode.RUNTIME.name());
            return ResponseEntity.badRequest().body(new ErrorResponse(CommonStateCode.RUNTIME));
        }
    }

    @GetMapping("/name")
    public ResponseEntity<Object> findCodeByName(FindCodeByNameReqDto findCodeByNameReqDto) {
        log.info("findCodeByName - Call");
        try {
            CommonCodeDto commonCodeDto = commonService.findCodeByName(
                findCodeByNameReqDto.getName());
            log.info("code : {}, message : {}", CommonStateCode.SUCCESS.getCode(),
                CommonStateCode.SUCCESS.name());
            return ResponseEntity.ok().body(commonCodeDto);
        } catch (NotFoundException e) {
            log.info("code : {}, message : {}", CommonStateCode.NOTFOUND_COMMONCODE.getCode(),
                CommonStateCode.NOTFOUND_COMMONCODE.name());
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(CommonStateCode.NOTFOUND_COMMONCODE));
        } catch (RuntimeException e) {
            log.info("code : {}, message : {}", CommonStateCode.RUNTIME.getCode(),
                CommonStateCode.RUNTIME.name());
            return ResponseEntity.badRequest().body(new ErrorResponse(CommonStateCode.RUNTIME));
        }
    }

    @GetMapping("/level")
    public ResponseEntity<Object> findRandomMong(FindRandomMongReqDto findRandomMongReqDto) {
        log.info("findRandomMong - Call");
        try {
            CommonCodeDto commonCodeDto = commonService.findRandomMong(findRandomMongReqDto);
            log.info("code : {}, message : {}", CommonStateCode.SUCCESS.getCode(),
                CommonStateCode.SUCCESS.name());
            return ResponseEntity.ok().body(commonCodeDto);
        } catch (NotFoundException e) {
            log.info("code : {}, message : {}", CommonStateCode.NOTFOUND_COMMONCODE.getCode(),
                CommonStateCode.NOTFOUND_COMMONCODE.name());
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(CommonStateCode.NOTFOUND_COMMONCODE));
        } catch (RuntimeException e) {
            log.info("code : {}, message : {}", CommonStateCode.RUNTIME.getCode(),
                CommonStateCode.RUNTIME.name());
            return ResponseEntity.badRequest().body(new ErrorResponse(CommonStateCode.RUNTIME));
        }
    }

}
