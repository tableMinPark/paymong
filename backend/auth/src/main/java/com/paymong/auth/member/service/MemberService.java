package com.paymong.auth.member.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.auth.auth.entity.Member;
import com.paymong.auth.auth.repository.MemberRepository;
import com.paymong.auth.global.client.ManagementServiceClient;
import com.paymong.auth.global.exception.NotFoundException;
import com.paymong.auth.global.vo.request.FindMongReqVo;
import com.paymong.auth.global.vo.response.FindMongResVo;
import com.paymong.auth.member.dto.response.FindMemberInfoResDto;
import com.paymong.auth.member.dto.response.ModifyPointResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final ManagementServiceClient managementServiceClient;

    @Transactional
    public Member findByMemberPlayerId(String playerId) throws RuntimeException {
        return memberRepository.findByPlayerId(playerId).orElseThrow();
    }

    @Transactional
    public FindMemberInfoResDto findMemberInfo() throws RuntimeException {
        String playerId = SecurityContextHolder.getContext().getAuthentication().getName();

        Member member = memberRepository.findByPlayerId(playerId)
            .orElseThrow(() -> new NotFoundException());

        ObjectMapper om = new ObjectMapper();

        FindMongResVo findMongResVo;

        try {
            findMongResVo = om.convertValue(
                managementServiceClient.findMongByMember(
                    new FindMongReqVo(member.getMemberId())).getBody(), FindMongResVo.class);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new FindMemberInfoResDto(null, member.getPoint());
        }

        return new FindMemberInfoResDto(findMongResVo.getMongId(), member.getPoint());
    }

    @Transactional
    public ModifyPointResDto modifyPoint(Long memberId, Integer point) throws RuntimeException {
        Member member = memberRepository.findByMemberId(memberId)
            .orElseThrow(() -> new NotFoundException());
        Long prePoint = member.getPoint();
        member.setPoint(prePoint + point);
        return new ModifyPointResDto(member.getPoint());
    }

}
