package com.paymong.auth.member.controller;

import com.paymong.auth.global.code.ErrorStateCode;
import com.paymong.auth.global.exception.NotFoundException;
import com.paymong.auth.global.exception.NullPointerException;
import com.paymong.auth.global.response.ErrorResponse;
import com.paymong.auth.member.dto.request.ModifyPointReqDto;
import com.paymong.auth.member.dto.response.FindMemberInfoResDto;
import com.paymong.auth.member.dto.response.ModifyPointResDto;
import com.paymong.auth.member.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/member")
public class MemberController {

    private final MemberService memberService;

    @Value("${header.member}")
    String headerMember;

    @GetMapping("/info")
    public ResponseEntity<Object> findMemberInfo() {
        log.info("findMemberInfo - Call");
        try {
            FindMemberInfoResDto findMemberInfoResDto = memberService.findMemberInfo();
            return ResponseEntity.ok().body(findMemberInfoResDto);
        } catch (NotFoundException e) {
            return ResponseEntity.ok().body(new ErrorResponse(ErrorStateCode.NOTFOUNDUSER));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }

    @PutMapping("/paypoint")
    public ResponseEntity<Object> modifyPoint(@RequestBody ModifyPointReqDto modifyPointDto,
        HttpServletRequest httpServletRequest) {
        log.info("modifyPoint - Call");
        try {
            Long memberId = Long.parseLong(httpServletRequest.getHeader(headerMember));
            if (memberId == null) {
                throw new NullPointerException();
            }
            ModifyPointResDto modifyPointResDto = memberService.modifyPoint(memberId,
                modifyPointDto.getPoint());
            return ResponseEntity.ok().body(modifyPointResDto);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.NOTFOUNDUSER));
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.NULLPOINTER));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }


}
