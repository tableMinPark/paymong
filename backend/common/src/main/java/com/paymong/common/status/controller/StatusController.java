package com.paymong.common.status.controller;

import com.paymong.common.global.code.CommonStateCode;
import com.paymong.common.global.exception.NotFoundException;
import com.paymong.common.global.response.ErrorResponse;
import com.paymong.common.status.dto.request.FindStatusReqDto;
import com.paymong.common.status.dto.response.FindStatusResDto;
import com.paymong.common.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/common/status")
public class StatusController {

    private final StatusService statusService;

    @GetMapping("/detail")
    public ResponseEntity<Object> findStatus(FindStatusReqDto findStatusReqDto) {
        log.info("findStatus - Call");
        try {
            FindStatusResDto findStatusResDto = statusService.findStatus(findStatusReqDto);
            log.info("code : {}, message : {}", CommonStateCode.SUCCESS.getCode(), CommonStateCode.SUCCESS.name());
            return ResponseEntity.ok().body(findStatusResDto);
        } catch (NotFoundException e) {
            log.info("code : {}, message : {}", CommonStateCode.NOTFOUND_COMMONCODE.getCode(), CommonStateCode.NOTFOUND_COMMONCODE.name());
            return ResponseEntity.badRequest().body(new ErrorResponse(CommonStateCode.NOTFOUND_COMMONCODE));
        } catch (RuntimeException e) {
            log.info("code : {}, message : {}", CommonStateCode.RUNTIME.getCode(), CommonStateCode.RUNTIME.name());
            return ResponseEntity.badRequest().body(new ErrorResponse(CommonStateCode.RUNTIME));
        }
    }
}
