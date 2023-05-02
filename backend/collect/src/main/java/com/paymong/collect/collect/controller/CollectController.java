package com.paymong.collect.collect.controller;

import com.paymong.collect.collect.dto.response.FindAllMapCollectResDto;
import com.paymong.collect.collect.dto.response.FindAllMongCollectResDto;
import com.paymong.collect.collect.service.CollectService;
import com.paymong.collect.global.code.ErrorStateCode;
import com.paymong.collect.global.response.ErrorResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Object> test(@RequestHeader(value = "MemberKey") String memberKey,
        @RequestHeader(value = "MongKey") String mongKey) {
        log.info("collect/test - Call");
        log.info("memberKey - {}", memberKey);
        log.info("mongKey - {}", mongKey.toString());

        if (mongKey.isBlank()) {
            log.info("mongKey is emtpy");
        }

        try {
            return ResponseEntity.ok().body("success");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("fail");
        }
    }

    @GetMapping("/map/list")
    public ResponseEntity<Object> findAllMapCollect(
        @RequestHeader(value = "MemberKey") String memberKey) {
        try {
            List<FindAllMapCollectResDto> findAllMapCollectResDto = collectService.findAllMapCollect(
                memberKey);
            return ResponseEntity.ok().body(findAllMapCollectResDto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.MAP_RUNTIME));
        }
    }

    @GetMapping("/character/list")
    public ResponseEntity<Object> findAllMongCollect(
        @RequestHeader(value = "MemberKey") String memberKey) {
        try {
            FindAllMongCollectResDto findAllMapCollectResDto = collectService.findAllMongCollect(
                memberKey);
            return ResponseEntity.ok().body(findAllMapCollectResDto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.MONG_RUNTIME));
        }
    }
}
