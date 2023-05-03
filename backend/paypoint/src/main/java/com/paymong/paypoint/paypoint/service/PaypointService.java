package com.paymong.paypoint.paypoint.service;

import com.paymong.paypoint.global.client.AuthServiceClient;
import com.paymong.paypoint.global.client.CollectServiceClient;
import com.paymong.paypoint.global.client.CommonServiceClient;
import com.paymong.paypoint.paypoint.dto.*;
import com.paymong.paypoint.paypoint.entity.PointHistory;
import com.paymong.paypoint.paypoint.repository.PaypointRepository;
import com.paymong.paypoint.global.pay.Pay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaypointService {
    private final PaypointRepository paypointRepository;
    private final CommonServiceClient commonServiceClient;
    private final AuthServiceClient authServiceClient;
    private final CollectServiceClient collectServiceClient;

    public void addPay(String memberIdStr, String mongIdStr, AddPayReqDto addPaypointReqDto) throws Exception{
        Long memberId = Long.parseLong(memberIdStr);
        Long mongId = 0L;
        if(!mongIdStr.equals("")) mongId = Long.parseLong(mongIdStr);
        String action = "페이 "+ addPaypointReqDto.getContent() + " 결제";
        Integer price = addPaypointReqDto.getPrice();
        PointHistory pointHistory = PointHistory.builder()
                .price(price)
                .action(action)
                .memberId(memberId)
                .mongId(mongId)
                .build();
        PointHistory ret =  paypointRepository.save(pointHistory);
        //가격 반영보내기
        //authServiceClient.modifyPaypoint(memberIdStr, mongIdStr, new ModifyPaypointReqDto(price));

        String map = Pay.getMap();

        //맵코드 받기
        //FindMapByNameResDto findMapByNameResDto =
        //commonServiceClient.findMapByName(memberIdStr, mongIdStr, new FindMapByNameReqDto(map));

        //맵코드보내기  /collect/map
        //collectServiceClient.addMap(memberIdStr, mongIdStr, new AddMapReqDto());

        System.out.println(ret);



    }


    public PointHistory addPoint(String memberIdStr, String mongIdStr, AddPointReqDto addPointReqDto) throws Exception{
        Long memberId = Long.parseLong(memberIdStr);
        Long mongId = Long.parseLong(mongIdStr);
        String action = addPointReqDto.getContent();
        int price = addPointReqDto.getPrice();
        PointHistory pointHistory = PointHistory.builder()
                .price(price)
                .action(action)
                .memberId(memberId)
                .mongId(mongId)
                .build();
        PointHistory ret =  paypointRepository.save(pointHistory);
        return ret;
    }

    public List<PointHistory> findAllPaypoint(String memberKey){
        Long memberId = Long.parseLong(memberKey);
        List<PointHistory> paypointList =  paypointRepository.findAllByMemberIdOrderByPointHistoryId(memberId);
        System.out.println(paypointList);
        return paypointList;
    }
}
