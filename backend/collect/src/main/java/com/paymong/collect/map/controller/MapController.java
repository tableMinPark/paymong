package com.paymong.collect.map.controller;

import com.paymong.collect.global.code.ErrorStateCode;
import com.paymong.collect.global.exception.GatewayException;
import com.paymong.collect.global.exception.NotFoundException;
import com.paymong.collect.global.response.ErrorResponse;
import com.paymong.collect.map.dto.request.AddMapReqDto;
import com.paymong.collect.map.dto.response.FindAllMapCollectResDto;
import com.paymong.collect.map.service.MapService;
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
@RequestMapping("/collect/map")
public class MapController {

    @Value("${header.member}")
    String headerMember;

    @Value("${header.mong}")
    String headerMong;

    private final MapService mapService;

    @GetMapping("/list")
    public ResponseEntity<Object> findAllMapCollect(
        HttpServletRequest httpServletRequest) {
        log.info("findAllMapCollect - Call");
        try {
            Long memberId = Long.parseLong(httpServletRequest.getHeader(headerMember));
            List<FindAllMapCollectResDto> findAllMapCollectResDto = mapService.findAllMapCollect(
                memberId);
            return ResponseEntity.ok().body(findAllMapCollectResDto);
        } catch (GatewayException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.BAD_GATEWAY));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.MAP_RUNTIME));
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> addMap(@RequestBody AddMapReqDto addMapReqDto,
        HttpServletRequest httpServletRequest) {
        Long memberId = Long.parseLong(httpServletRequest.getHeader(headerMember));
        try {
            log.info("findMap - Call");
            mapService.findMap(memberId, addMapReqDto.getCode());
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.info("addMap - Call");
            mapService.addMap(memberId, addMapReqDto.getCode());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.MAP_RUNTIME));
        }
    }

}
