package com.paymong.paypoint.paypoint.controller;

import com.paymong.paypoint.paypoint.dto.AddPaypointReqDto;
import com.paymong.paypoint.paypoint.service.PaypointService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/paypoint")
public class PaypointController {
    final public PaypointService paypointService;
    @PostMapping("")
    public ResponseEntity<Object> addPaypoint(@RequestHeader(value = "MemberKey") String memberKey,
                                              @RequestBody AddPaypointReqDto addPaypointReqDto
                                              ){
        log.info("addPaypoint - Call");
        try {
            paypointService.addPaypoint(Long.parseLong(memberKey), addPaypointReqDto);
        }catch (Exception e){

        }
        return null;
    }


    @GetMapping("/list")
    public ResponseEntity<Object> findAllPaypoint(@RequestHeader(value = "MemberKey") String memberKey){
        log.info("addPaypoint - Call");
        try {
            paypointService.findAllPaypoint(Long.parseLong(memberKey));
        }catch (Exception e){

        }
        return null;
    }

}
