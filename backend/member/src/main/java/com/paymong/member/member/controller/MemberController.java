package com.paymong.member.member.controller;

import com.paymong.auth.global.code.ErrorStateCode;
import com.paymong.auth.global.exception.NotFoundException;
import com.paymong.auth.global.exception.NullPointerException;
import com.paymong.auth.global.exception.PayPointException;
import com.paymong.auth.global.response.ErrorResponse;
import com.paymong.member.global.exception.NotFoundException;
import com.paymong.member.global.exception.PayPointException;
import com.paymong.member.member.dto.request.ModifyPointReqDto;
import com.paymong.member.member.dto.response.FindMemberInfoResDto;
import com.paymong.member.member.dto.response.ModifyPointResDto;
import com.paymong.member.member.service.MemberService;
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
@RequestMapping("/member")
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
            log.error(ErrorStateCode.NOTFOUNDUSER.getMessage());
            return ResponseEntity.ok().body(new ErrorResponse(ErrorStateCode.NOTFOUNDUSER));
        } catch (RuntimeException e) {
            log.error(ErrorStateCode.RUNTIME.getMessage());
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
            log.error(ErrorStateCode.NOTFOUNDUSER.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.NOTFOUNDUSER));
        } catch (NullPointerException e) {
            log.error(ErrorStateCode.NULLPOINTER.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.NULLPOINTER));
        } catch (RuntimeException e) {
            log.error(ErrorStateCode.RUNTIME.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }
    }

    @PutMapping("/management/paypoint")
    public ResponseEntity<Object> modifyPointAndToPaypoint(
        @RequestBody ModifyPointReqDto modifyPointDto,
        HttpServletRequest httpServletRequest) {
        log.info("modifyPointAndToPaypoint - Call");

        log.info("modifyPointDto content -{} ", modifyPointDto.getContent());
        log.info("modifyPointDto point -{} ", modifyPointDto.getPoint());
        try {
            Long memberId = Long.parseLong(httpServletRequest.getHeader(headerMember));
            memberService.modifyPointAndToPaypoint(memberId, modifyPointDto.getPoint(),
                modifyPointDto.getContent());
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            log.error(ErrorStateCode.NOTFOUNDUSER.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.NOTFOUNDUSER));
        } catch (PayPointException e) {
            log.error(ErrorStateCode.PAYPOINT.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.PAYPOINT));
        } catch (Exception e) {
            log.error(ErrorStateCode.RUNTIME.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorStateCode.RUNTIME));
        }

    }


}
