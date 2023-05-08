package com.paymong.member.member.controller;

import com.paymong.member.global.code.PaypointStateCode;
import com.paymong.member.global.exception.NotFoundException;
import com.paymong.member.global.response.ErrorResponse;
import com.paymong.member.member.dto.request.LoginReqDto;
import com.paymong.member.member.dto.request.ModifyPointReqDto;
import com.paymong.member.member.dto.request.SecureReqDto;
import com.paymong.member.member.dto.response.FindMemberInfoResDto;
import com.paymong.member.member.dto.response.LoginResDto;
import com.paymong.member.member.dto.response.ModifyPointResDto;
import com.paymong.member.member.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<Object> findMemberInfo(HttpServletRequest httpServletRequest) {
        log.info("findMemberInfo - Call");
        Long memberId = Long.parseLong(httpServletRequest.getHeader(headerMember));
        try {
            FindMemberInfoResDto findMemberInfoResDto = memberService.findMemberInfo(memberId);
            return ResponseEntity.ok().body(findMemberInfoResDto);
        } catch (NotFoundException e) {
            log.error(PaypointStateCode.NOTEXIST.getMessage());
            return ResponseEntity.ok().body(new ErrorResponse(PaypointStateCode.NOTEXIST));
        } catch (RuntimeException e) {
            log.error(PaypointStateCode.UNKNOWN.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(PaypointStateCode.UNKNOWN));
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
            log.error(PaypointStateCode.NOTEXIST.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(PaypointStateCode.NOTEXIST));
        } catch (NullPointerException e) {
            log.error(PaypointStateCode.UNAVAILABLE.getMessage());
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(PaypointStateCode.UNAVAILABLE));
        } catch (RuntimeException e) {
            log.error(PaypointStateCode.UNKNOWN.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(PaypointStateCode.UNKNOWN));
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
            log.error(PaypointStateCode.NOTEXIST.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(PaypointStateCode.NOTEXIST));
        } catch (Exception e) {
            log.error(PaypointStateCode.UNKNOWN.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginReqDto loginReqDto){
        log.info("Member login - Call");
        try{
            LoginResDto loginResDto = memberService.login(loginReqDto);
            return ResponseEntity.ok().body(loginResDto);
        }catch (NotFoundException e){
            try{
                LoginResDto loginResDto =memberService.register(loginReqDto);
                return ResponseEntity.ok().body(loginResDto);
            }catch (Exception ex){
                return ResponseEntity.badRequest().body(new ErrorResponse(PaypointStateCode.UNKNOWN));
            }
        } catch(Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }
    }

    @PostMapping("/secure")
    public ResponseEntity<Object> secure(@RequestBody SecureReqDto secureReqDto){
        log.info("Member secure - Call");
        try{
            memberService.secure(secureReqDto.getPlayerId());
            return ResponseEntity.ok().build();
        } catch (NotFoundException e){
            return ResponseEntity.badRequest().body(new ErrorResponse(PaypointStateCode.NOTEXIST));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse(PaypointStateCode.UNKNOWN));
        }
    }

}
