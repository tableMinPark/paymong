package com.paymong.common.status.controller;

import com.paymong.common.global.code.ErrorStateCode;
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
        try {
            FindStatusResDto findStatusResDto = statusService.findStatus(findStatusReqDto);
            return ResponseEntity.ok().body(findStatusResDto);
        } catch (NotFoundException e) {
            return ResponseEntity.ok().body(new ErrorResponse(ErrorStateCode.NOTFOUND_COMMONCODE));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }
}
