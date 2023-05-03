package com.paymong.paypoint.paypoint.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.paypoint.global.client.AuthServiceClient;
import com.paymong.paypoint.global.client.CollectServiceClient;
import com.paymong.paypoint.global.client.CommonServiceClient;
import com.paymong.paypoint.global.exception.NotFoundAuthException;
import com.paymong.paypoint.global.exception.NotFoundMapCodeException;
import com.paymong.paypoint.global.exception.NotFoundMapException;
import com.paymong.paypoint.paypoint.dto.*;
import com.paymong.paypoint.paypoint.entity.PointHistory;
import com.paymong.paypoint.paypoint.repository.PaypointRepository;
import com.paymong.paypoint.global.pay.Pay;
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
    private final CommonServiceClient commonServiceClient;
    private final AuthServiceClient authServiceClient;
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


        //가격 반영보내기
        ResponseEntity<Object> authResponse  = authServiceClient.modifyPaypoint(memberIdStr, new ModifyPaypointReqDto(point));
        ObjectMapper om = new ObjectMapper();
        if(authResponse.getStatusCode()== HttpStatus.BAD_REQUEST) throw new NotFoundAuthException();
        ModifyPaypointReqDto modifyPaypointReqDto = om.convertValue(authResponse.getBody(),ModifyPaypointReqDto.class);
        Integer totalPoint = modifyPaypointReqDto.getPoint();

        //브랜드명 뽑기(없으면 null)
        String brand = Pay.getMap(action);

        AddPaypointResDto addPayResDto = AddPaypointResDto.builder()
                .point(totalPoint)
                .build();
        if (brand != null){
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
        int point = addPointReqDto.getPoint();

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
