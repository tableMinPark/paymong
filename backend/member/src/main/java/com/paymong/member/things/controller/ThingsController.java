package com.paymong.member.things.controller;

import com.paymong.member.global.code.PaypointStateCode;
import com.paymong.member.global.response.ErrorResponse;
import com.paymong.member.things.dto.request.AddThingsReqDto;
import com.paymong.member.things.dto.request.RemoveThingsReqDto;
import com.paymong.member.things.dto.response.FindThingsListResDto;
import com.paymong.member.things.entity.Things;
import com.paymong.member.things.service.ThingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/things")
public class ThingsController {

    private final ThingsService thingsService;

    @GetMapping("")
    public ResponseEntity<Object> findThingsList(@RequestHeader(value = "MemberId") String memberIdStr) {
        log.info("findThingsList - Call");

        try {
            List<FindThingsListResDto> ret = thingsService.findThingsList(memberIdStr);
            return ResponseEntity.ok().body(ret);
        } catch (Exception e) {
            log.error(PaypointStateCode.UNKNOWN.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }
    }

    @GetMapping("/addable")
    public ResponseEntity<Object> findAddableThings(@RequestHeader(value = "MemberId") String memberIdStr) {
        log.info("findAddableThings - Call");

        try {

            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            log.error(PaypointStateCode.UNKNOWN.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> addThings(@RequestHeader(value = "MemberId") String memberIdStr,
                                            @RequestBody AddThingsReqDto addThingsResDto) {
        log.info("addThings - Call");

        try {
            Things ret = thingsService.addThings(memberIdStr, addThingsResDto);
            return ResponseEntity.ok().body(ret);
        } catch (Exception e) {
            log.error(PaypointStateCode.UNKNOWN.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }
    }

    @DeleteMapping("")
    public ResponseEntity<Object> removeThings(@RequestHeader(value = "MemberId") String memberIdStr,
                                               @RequestBody RemoveThingsReqDto removeThingsReqDto) {
        log.info("removeThings - Call");
        try {
            thingsService.removeThings(memberIdStr, removeThingsReqDto);
        }catch (Exception e){
            log.error(PaypointStateCode.UNKNOWN.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }

        return null;
    }
}
