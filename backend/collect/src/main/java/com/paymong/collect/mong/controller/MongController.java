package com.paymong.collect.mong.controller;

import com.paymong.collect.global.code.ErrorStateCode;
import com.paymong.collect.global.exception.CommonCodeException;
import com.paymong.collect.global.exception.NotFoundException;
import com.paymong.collect.global.response.ErrorResponse;
import com.paymong.collect.mong.dto.request.AddMongReqDto;
import com.paymong.collect.mong.dto.response.MongDto;
import com.paymong.collect.mong.service.MongService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/collect/mong")
public class MongController {

    @Value("${header.member}")
    String headerMember;

    @Value("${header.mong}")
    String headerMong;

    private final MongService mongService;

    @GetMapping("")
    public ResponseEntity<Object> findAllMongCollect(HttpServletRequest httpServletRequest) {
        log.info("findAllMongCollect - Call");
        Long memberId = Long.parseLong(httpServletRequest.getHeader(headerMember));
        try {
            List<MongDto> findAllMapCollectResDto = mongService.findAllMongCollect(
                memberId);
            log.info("code : {}, message : {}", ErrorStateCode.SUCCESS.getCode(),
                ErrorStateCode.SUCCESS.getMessage());
            return ResponseEntity.ok().body(findAllMapCollectResDto);
        } catch (CommonCodeException e) {
            log.info("code : {}, message : {}", ErrorStateCode.COMMONCODE.getCode(),
                ErrorStateCode.COMMONCODE.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.COMMONCODE));
        } catch (RuntimeException e) {
            log.info("code : {}, message : {}", ErrorStateCode.RUNTIME.getCode(),
                ErrorStateCode.RUNTIME.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> addMong(@RequestBody AddMongReqDto addMongReqDto,
        HttpServletRequest httpServletRequest) throws RuntimeException {
        Long memberId = Long.parseLong(httpServletRequest.getHeader(headerMember));
        try {
            log.info("findMong - Call");
            mongService.findMong(memberId, addMongReqDto.getCode());
            log.info("code : {}, message : {}", ErrorStateCode.SUCCESS.getCode(),
                ErrorStateCode.SUCCESS.getMessage());
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.info("addMong - Call");
            try {
                mongService.addMong(memberId, addMongReqDto.getCode());
                log.info("code : {}, message : {}", ErrorStateCode.SUCCESS.getCode(),
                    ErrorStateCode.SUCCESS.getMessage());
                return ResponseEntity.ok().build();
            } catch (Exception ex) {
                log.error("code : {}, message : {}", ErrorStateCode.RUNTIME.getCode(),
                    ErrorStateCode.RUNTIME.getMessage());
                return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
            }
        } catch (RuntimeException e) {
            log.error("code : {}, message : {}", ErrorStateCode.RUNTIME.getCode(),
                ErrorStateCode.RUNTIME.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }

}
