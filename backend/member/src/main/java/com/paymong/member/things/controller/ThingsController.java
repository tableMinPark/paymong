package com.paymong.member.things.controller;

import com.paymong.member.global.code.PaypointStateCode;
import com.paymong.member.global.code.ThingsStateCode;
import com.paymong.member.global.exception.NotFoundRoutineException;
import com.paymong.member.global.exception.NotFoundThingsCodeException;
import com.paymong.member.global.response.ErrorResponse;
import com.paymong.member.global.response.SuccessResponse;
import com.paymong.member.things.dto.request.AddThingsReqDto;
import com.paymong.member.things.dto.request.GetAlarmReqDto;
import com.paymong.member.things.dto.request.RemoveThingsReqDto;
import com.paymong.member.things.dto.response.FindAddableThingsResDto;
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
            log.error(ThingsStateCode.UNKNOWN.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ThingsStateCode.UNKNOWN));
        }
    }

    @GetMapping("/addable")
    public ResponseEntity<Object> findAddableThings(@RequestHeader(value = "MemberId") String memberIdStr) {
        log.info("findAddableThings - Call");

        try {
            List<FindAddableThingsResDto> ret = thingsService.findAddableThings(memberIdStr);
            return ResponseEntity.ok().body(ret);
        } catch (Exception e) {
            System.out.println(e);
            log.error(ThingsStateCode.UNKNOWN.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ThingsStateCode.UNKNOWN));
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
            log.error(ThingsStateCode.UNKNOWN.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ThingsStateCode.UNKNOWN));
        }
    }

    @DeleteMapping("")
    public ResponseEntity<Object> removeThings(@RequestHeader(value = "MemberId") String memberIdStr,
                                               @RequestBody RemoveThingsReqDto removeThingsReqDto) {
        log.info("removeThings - Call");
        try {
            thingsService.removeThings(memberIdStr, removeThingsReqDto);
            return ResponseEntity.ok().body(new SuccessResponse(ThingsStateCode.SUCCESS));
        }catch (Exception e){
            log.error(ThingsStateCode.UNKNOWN.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ThingsStateCode.UNKNOWN));
        }
    }

    @PostMapping("/alarm")
    public ResponseEntity<Object> getThingsAlarm(@RequestHeader(value = "MemberId") String memberIdStr,
                                           @RequestHeader(value = "MongId") String mongIdStr,
                                           @RequestBody GetAlarmReqDto getAlarmReqDto) {
        log.info("getThingsAlarm - Call");
        Long memberId = Long.parseLong(memberIdStr);
        String routine = getAlarmReqDto.getRoutine();
        try {
            String thingsCode = thingsService.findThingsCode(memberId, routine);
            if (thingsCode.equals("ST000")){ //청소기
                thingsService.doVacuum(memberIdStr, mongIdStr, thingsCode);
                System.out.println("청소기 센서~");
            }else if (thingsCode.equals("ST001")){ //문열림센서

            }else if (thingsCode.equals("ST002")){ //허브무선충전

            }else if (thingsCode.equals("ST003")){ //스마트버튼

            }else{
                throw new NotFoundThingsCodeException();
            }
            return ResponseEntity.ok().body(new SuccessResponse(ThingsStateCode.SUCCESS));
        }catch (NotFoundThingsCodeException e){
            log.error(ThingsStateCode.THINGS_CODE_ERROR.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ThingsStateCode.THINGS_CODE_ERROR));
        }catch (NotFoundRoutineException e){
            log.error(ThingsStateCode.ROUTINE_ERROR.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ThingsStateCode.ROUTINE_ERROR));
        }catch (Exception e){
            System.out.println(e);
            log.error(ThingsStateCode.UNKNOWN.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ThingsStateCode.UNKNOWN));
        }


    }

}
