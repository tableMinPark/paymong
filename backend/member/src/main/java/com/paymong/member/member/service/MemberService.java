package com.paymong.member.member.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.member.global.client.ManagementServiceClient;
import com.paymong.member.global.exception.NotFoundException;
import com.paymong.member.member.dto.request.LoginReqDto;
import com.paymong.member.member.dto.response.FindMemberInfoResDto;
import com.paymong.member.member.dto.response.LoginResDto;
import com.paymong.member.member.dto.response.ModifyPointResDto;
import com.paymong.member.member.entity.Member;
import com.paymong.member.member.repository.MemberRepository;
import com.paymong.member.member.vo.FindMongReqVo;
import com.paymong.member.member.vo.FindMongResVo;
import com.paymong.member.paypoint.dto.response.AddPointReqDto;
import com.paymong.member.paypoint.service.PaypointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final ManagementServiceClient managementServiceClient;


    private final PaypointService paypointService;


    @Transactional
    public Member findByMemberPlayerId(String playerId) throws RuntimeException {
        return memberRepository.findByPlayerId(playerId).orElseThrow();
    }

    @Transactional
    public FindMemberInfoResDto findMemberInfo(Long memberId) throws RuntimeException {
//        String playerId = SecurityContextHolder.getContext().getAuthentication().getName();

        Member member = memberRepository.findByMemberId(memberId)
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
        Integer prePoint = member.getPoint();
        member.setPoint(prePoint + point);
        return new ModifyPointResDto(member.getPoint());
    }

    @Transactional
    public void modifyPointAndToPaypoint(Long memberId, Integer point, String content)
        throws Exception {

        Member member = memberRepository.findByMemberId(memberId)
            .orElseThrow(() -> new NotFoundException());

        String memberIdStr = Long.toString(memberId);
        paypointService.addPoint(memberIdStr, new AddPointReqDto(content, point));

        Integer prePoint = member.getPoint();
        member.setPoint(prePoint + point);
    }

    @Transactional
    public LoginResDto login(LoginReqDto loginReqDto) {
        Member member = memberRepository.findByPlayerId(loginReqDto.getPlayerId())
            .orElseThrow(() -> new NotFoundException());
        return LoginResDto.builder().point(member.getPoint())
            .memberId(member.getMemberId()).build();
    }

    @Transactional
    public LoginResDto register(LoginReqDto loginReqDto) {
        Member member = Member.builder().password(loginReqDto.getPassword())
            .playerId(loginReqDto.getPlayerId()).point(0).build();
        memberRepository.save(member);

        //맵정해주자 여기서!
        //~~


        return LoginResDto.builder().point(member.getPoint()).memberId(member.getMemberId()).build();
    }

    @Transactional
    public void secure(String palyerId) {
        Member member = memberRepository.findByPlayerId(palyerId)
            .orElseThrow(() -> new NotFoundException());

    }

}
