package com.paymong.paypoint.paypoint.service;

import com.paymong.paypoint.paypoint.dto.AddPaypointReqDto;
import com.paymong.paypoint.paypoint.dto.FindMapByNameResDto;
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
    final public PaypointRepository paypointRepository;

    public PointHistory addPay(String memberKey, String mongKey, AddPaypointReqDto addPaypointReqDto) throws Exception{
        Long memberId = Long.parseLong(memberKey);
        Long mongId = 0L;
        if(!mongKey.equals("")) mongId = Long.parseLong(mongKey);
        String action = "페이 "+ addPaypointReqDto.getContent() + " 결제";
        PointHistory pointHistory = PointHistory.builder()
                .price(addPaypointReqDto.getPrice())
                .action(action)
                .memberId(memberId)
                .mongId(mongId)
                .build();
        PointHistory ret =  paypointRepository.save(pointHistory);
        //가격 반영보내기
        String map = Pay.getMap();
        FindMapByNameResDto findMapByNameResDto =
        //맵보내기

        System.out.println(ret);

        return ret;

    }


    public void addPoint(String memberKey, AddPaypointReqDto addPaypointReqDto) throws Exception{
        Long memberId = Long.parseLong(memberKey);
        PointHistory pointHistory = PointHistory.builder()
                .price(addPaypointReqDto.getPrice())
                .action("")
                .build();

    }

    public void findAllPaypoint(String memberKey){
        Long memberId = Long.parseLong(memberKey);
        List<PointHistory> paypointList =  paypointRepository.findAllByMemberIdOrderByPointHistoryId(memberId);
        System.out.println(paypointList);
    }
}
