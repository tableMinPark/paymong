package com.paymong.paypoint.paypoint.service;

import com.paymong.paypoint.paypoint.dto.AddPaypointReqDto;
import com.paymong.paypoint.paypoint.entity.PointHistory;
import com.paymong.paypoint.paypoint.repository.PaypointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaypointService {
    final public PaypointRepository paypointRepository;
    public void addPaypoint(Long memberId, AddPaypointReqDto addPaypointReqDto) throws Exception{
        List<PointHistory> paypointList =  paypointRepository.findAllByMemberIdOrderByPointHistoryId(memberId);
        System.out.println(paypointList);
    }
    public void findAllPaypoint(Long memberId){
        List<PointHistory> paypointList =  paypointRepository.findAllByMemberIdOrderByPointHistoryId(memberId);
        System.out.println(paypointList);
    }
}
