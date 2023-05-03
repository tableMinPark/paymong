package com.paymong.collect.mong.controller;

import com.paymong.collect.global.code.ErrorStateCode;
import com.paymong.collect.global.exception.GatewayException;
import com.paymong.collect.global.exception.NotFoundException;
import com.paymong.collect.global.response.ErrorResponse;
import com.paymong.collect.mong.dto.request.AddMongReqDto;
import com.paymong.collect.mong.dto.response.FindAllMongCollectResDto;
import com.paymong.collect.mong.service.MongService;
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

    @GetMapping("/list")
    public ResponseEntity<Object> findAllMongCollect(HttpServletRequest httpServletRequest) {
        log.info("findAllMongCollect - Call");
        Long memberId = Long.parseLong(httpServletRequest.getHeader(headerMember));
        try {
            FindAllMongCollectResDto findAllMapCollectResDto = mongService.findAllMongCollect(
                memberId);
            return ResponseEntity.ok().body(findAllMapCollectResDto);
        } catch (GatewayException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.BAD_GATEWAY));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.MONG_RUNTIME));
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> addMong(@RequestBody AddMongReqDto addMongReqDto,
        HttpServletRequest httpServletRequest) {
        Long memberId = Long.parseLong(httpServletRequest.getHeader(headerMember));
        try {
            mongService.findMong(memberId, addMongReqDto.getCode());
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.info("addMap - Call");
            mongService.addMong(memberId, addMongReqDto.getCode());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }

}
