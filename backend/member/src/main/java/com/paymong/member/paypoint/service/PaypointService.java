package com.paymong.member.paypoint.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.member.global.client.CollectServiceClient;
import com.paymong.member.global.client.CommonServiceClient;
import com.paymong.member.global.exception.NotFoundException;
import com.paymong.member.global.exception.NotFoundMapCodeException;
import com.paymong.member.global.exception.NotFoundMapException;
import com.paymong.member.member.entity.Member;
import com.paymong.member.member.repository.MemberRepository;
import com.paymong.member.paypoint.dto.request.AddMapReqDto;
import com.paymong.member.paypoint.dto.request.AddPaypointReqDto;
import com.paymong.member.paypoint.dto.request.FindMapByNameReqDto;
import com.paymong.member.paypoint.dto.response.AddPaypointResDto;
import com.paymong.member.paypoint.dto.response.AddPointReqDto;
import com.paymong.member.paypoint.dto.response.FindMapByNameResDto;
import com.paymong.member.paypoint.entity.PointHistory;
import com.paymong.member.paypoint.repository.PaypointRepository;
import com.paymong.member.global.pay.Pay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaypointService {
    private final PaypointRepository paypointRepository;
    private final MemberRepository memberRepository;
    private final CommonServiceClient commonServiceClient;
    private final CollectServiceClient collectServiceClient;

    @Transactional
    public AddPaypointResDto addPaypoint(String memberIdStr, AddPaypointReqDto addPaypointReqDto) throws Exception{
        Long memberId = Long.parseLong(memberIdStr);
        String action = "페이 "+ addPaypointReqDto.getContent() + " 결제";
        Integer price = addPaypointReqDto.getPrice();
        Integer point = price/10;
        PointHistory pointHistory = PointHistory.builder()
                .point(point)
                .action(action)
                .memberId(memberId)
                .build();

        PointHistory ret =  paypointRepository.save(pointHistory);


        //가격 반영
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException());
        Integer prePoint = member.getPoint();
        member.setPoint(prePoint + point);
        Integer totalPoint = member.getPoint();

        //브랜드명 뽑기(없으면 null)
        String brand = Pay.getMap(action);

        AddPaypointResDto addPayResDto = AddPaypointResDto.builder()
                .point(totalPoint)
                .mapCode("MP000")
                .build();
        if (brand != null){
            ObjectMapper om = new ObjectMapper();
            //맵코드 받기
            ResponseEntity<Object> commonResponse  = commonServiceClient.findMapByName(memberIdStr, new FindMapByNameReqDto(brand));
            System.out.println(commonResponse.getBody());
            if(commonResponse.getStatusCode()== HttpStatus.BAD_REQUEST) throw new NotFoundMapException();

            FindMapByNameResDto findMapByNameResDto = om.convertValue(commonResponse.getBody(), FindMapByNameResDto.class);
            String mapCode = findMapByNameResDto.getCode();

            //맵코드보내기
            ResponseEntity<Object> collectResponse = collectServiceClient.addMap(memberIdStr, new AddMapReqDto(mapCode));
            if(collectResponse.getStatusCode()== HttpStatus.BAD_REQUEST) throw new NotFoundMapCodeException();

            addPayResDto.setMapCode(mapCode);
        }

        return addPayResDto;

    }


    @Transactional
    public PointHistory addPoint(String memberIdStr,  AddPointReqDto addPointReqDto) throws Exception{
        System.out.println(memberIdStr + " " + addPointReqDto.getPoint() +" " + addPointReqDto.getContent());
        Long memberId = Long.parseLong(memberIdStr);
        String action = addPointReqDto.getContent();
        Integer point = addPointReqDto.getPoint();
        
        //point반영
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException());
        Integer prePoint = member.getPoint();
        member.setPoint(prePoint + point);

        //history에 기록
        PointHistory pointHistory = PointHistory.builder()
                .point(point)
                .action(action)
                .memberId(memberId)
                .build();
        PointHistory ret =  paypointRepository.save(pointHistory);
        return ret;
    }

    @Transactional
    public List<PointHistory> findAllPaypoint(String memberKey){
        Long memberId = Long.parseLong(memberKey);
        List<PointHistory> paypointList =  paypointRepository.findAllByMemberIdOrderByPointHistoryIdDesc(memberId);
        System.out.println(paypointList);
        return paypointList;
    }
}
