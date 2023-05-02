package com.paymong.collect.collect.controller;

import com.paymong.collect.collect.dto.request.AddMapReqDto;
import com.paymong.collect.collect.dto.response.FindAllMapCollectResDto;
import com.paymong.collect.collect.dto.response.FindAllMongCollectResDto;
import com.paymong.collect.collect.service.CollectService;
import com.paymong.collect.global.code.ErrorStateCode;
import com.paymong.collect.global.exception.GatewayException;
import com.paymong.collect.global.response.ErrorResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/collect")
public class CollectController {

    private final CollectService collectService;

    @GetMapping("/test")
    public ResponseEntity<Object> test(@RequestHeader(value = "MemberId") String memberId,
        @RequestHeader(value = "MongId") String mongId) {
        log.info("collect/test - Call");
        log.info("memberId - {}", memberId);
        log.info("mongId - {}", mongId.toString());

        if (mongId.isBlank()) {
            log.info("mongId is emtpy");
        }

        try {
            return ResponseEntity.ok().body("success");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("fail");
        }
    }

    @GetMapping("/map/list")
    public ResponseEntity<Object> findAllMapCollect(
        @RequestHeader(value = "MemberId") String memberId) {
        try {
            List<FindAllMapCollectResDto> findAllMapCollectResDto = collectService.findAllMapCollect(
                memberId);
            return ResponseEntity.ok().body(findAllMapCollectResDto);
        } catch (GatewayException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.BAD_GATEWAY));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.MAP_RUNTIME));
        }
    }

//    @PostMapping("/map")
//    public ResponseEntity<Object> addMap(AddMapReqDto addMapReqDto) {
//        try {
//            collectService.addMap(addMapReqDto);
//            return ResponseEntity.ok().build();
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.MAP_RUNTIME));
//        }
//    }

    @GetMapping("/character/list")
    public ResponseEntity<Object> findAllMongCollect(
        @RequestHeader(value = "MemberId") String memberId) {
        try {
            FindAllMongCollectResDto findAllMapCollectResDto = collectService.findAllMongCollect(
                memberId);
            return ResponseEntity.ok().body(findAllMapCollectResDto);
        } catch (GatewayException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.BAD_GATEWAY));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.MONG_RUNTIME));
        }
    }


}
