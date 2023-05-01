package com.paymong.paypoint.paypoint.controller;

import com.paymong.paypoint.paypoint.dto.AddPaypointReqDto;
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
    @PostMapping("")
    public ResponseEntity<Object> addPaypoint(@RequestHeader(value = "MemberKey") String memberKey,
                                              @RequestHeader(value = "MongKey") String mongKey,
                                              @RequestBody AddPaypointReqDto addPaypointReqDto
                                              ){
        System.out.println(memberKey);
        System.out.println(mongKey);
        System.out.println(addPaypointReqDto);

        return null;
    }

}
