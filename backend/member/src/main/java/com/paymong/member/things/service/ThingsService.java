package com.paymong.member.things.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.member.global.client.CommonServiceClient;
import com.paymong.member.global.client.ManagementServiceClient;
import com.paymong.member.global.exception.NotFoundException;
import com.paymong.member.global.exception.NotFoundRoutineException;
import com.paymong.member.member.entity.Member;
import com.paymong.member.member.repository.MemberRepository;
import com.paymong.member.paypoint.entity.PointHistory;
import com.paymong.member.paypoint.repository.PaypointRepository;
import com.paymong.member.things.dto.request.AddThingsReqDto;
import com.paymong.member.things.dto.request.FindAddableThingsReqDto;
import com.paymong.member.things.dto.request.RemoveThingsReqDto;
import com.paymong.member.things.dto.response.FindAddableThingsResDto;
import com.paymong.member.things.dto.response.FindThingsListResDto;
import com.paymong.member.things.dto.response.ThingsCommonCode;
import com.paymong.member.things.dto.response.ThingsCommonCodeList;
import com.paymong.member.things.entity.Things;
import com.paymong.member.things.entity.ThingsHistory;
import com.paymong.member.things.repository.ThingsHistoryRepository;
import com.paymong.member.things.repository.ThingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThingsService {
    private final ThingsRepository thingsRepository;
    private final ThingsHistoryRepository thingsHistoryRepository;
    private final CommonServiceClient commonServiceClient;
    private final ManagementServiceClient managementServiceClient;
    private final MemberRepository memberRepository;
    private final PaypointRepository paypointRepository;

    @Transactional
    public List<FindThingsListResDto> findThingsList(String memberIdStr) throws Exception{
        Long memberId = Long.parseLong(memberIdStr);
        List<Things> thingsList= thingsRepository.findAllByMemberId(memberId);
        List<FindThingsListResDto> ret = thingsList.stream().map(things -> FindThingsListResDto.builder()
                        .thingsId(things.getThingsId())
                        .thingsName(things.getThingsName())
                        .thingsCode(things.getThingsCode())
                        .routine(things.getRoutine())
                        .regDt(things.getRegDt())
                        .build()
                    ).collect(Collectors.toList());
        return ret;
    }

    public List<FindAddableThingsResDto> findAddableThings(String memberIdStr) throws Exception{
        Long memberId = Long.parseLong(memberIdStr);
        String groupCode = "ST";
        FindAddableThingsReqDto req = new FindAddableThingsReqDto(groupCode);

        ObjectMapper om = new ObjectMapper();
        //things code 리스트받아오기
        ResponseEntity<Object> response =  commonServiceClient.findAllCommonCode(req);
        ThingsCommonCodeList thingsCommonCodeList =  om.convertValue(response.getBody(), ThingsCommonCodeList.class);

        //나의 things code 리스트 가져오기
        Map<String,Integer> check = new HashMap<>();
        List<Things> myThings = thingsRepository.findAllByMemberId(memberId);
        for(Things things : myThings) {
            check.put(things.getThingsCode(),1);
        }

        List<FindAddableThingsResDto> ret = new ArrayList<>();
        //나한테 없는 코드 찾기
        for( ThingsCommonCode thingsCode : thingsCommonCodeList.getCommonCodeDtoList()){
            if(!check.containsKey(thingsCode.getCode()))
                ret.add(new FindAddableThingsResDto(thingsCode.getCode(), thingsCode.getName()));

        }
        return ret;
    }


    public Things addThings(String memberIdStr, AddThingsReqDto addThingsReqDto) throws Exception{
        Long memberId = Long.parseLong(memberIdStr);
        //중복체크

        Things added = Things.builder()
                .memberId(memberId)
                .thingsCode(addThingsReqDto.getThingsCode())
                .thingsName(addThingsReqDto.getThingsName())
                .routine(addThingsReqDto.getRoutine())
                .build();
        thingsRepository.save(added);
        return  added;
    }


    @Transactional
    public void removeThings(String memberIdStr, RemoveThingsReqDto removeThingsReqDto) throws Exception{
        Long memberId = Long.parseLong(memberIdStr);
        Long thingsId = removeThingsReqDto.getThingsId();
        thingsRepository.deleteByMemberIdAndThingsId(memberId, thingsId);
    }

    public String findThingsCode(Long memberId, String routine) throws RuntimeException{
        Things things = thingsRepository.findByMemberIdAndRoutine(memberId, routine)
                .orElseThrow(() -> new NotFoundRoutineException());
        return things.getThingsCode();
    }

    @Transactional
    public void alarmVacuum(String memberIdStr, String mongIdStr, String thingsCode){
        Long memberId = Long.parseLong(memberIdStr);
        if(mongIdStr.equals("") || mongIdStr == null) return;

        //청소기 모션 요청 보내기


        ThingsHistory prevThingsHistory = thingsHistoryRepository.findTopByMemberIdAndThingsCodeOrderByThingsHistoryIdDesc(memberId, thingsCode);

        ThingsHistory thingsHistory = ThingsHistory.builder()
                .memberId(memberId)
                .thingsCode(thingsCode)
                .build();
        thingsHistoryRepository.save(thingsHistory);

        //똥치우기
        ResponseEntity<Object> response = managementServiceClient.clearPoop(mongIdStr);

        //하루한번만
        Integer toDay = LocalDateTime.now().getDayOfMonth();
        if (!(prevThingsHistory == null || prevThingsHistory.getRegDt().getDayOfMonth()!=toDay))
            return;

        //보너스 포인트 적립
        String action = "스마트싱스 청소 보너스";
        Integer point = 500;
        PointHistory pointHistory = PointHistory.builder()
                .point(point)
                .action(action)
                .memberId(memberId)
                .code(thingsCode)
                .build();
        paypointRepository.save(pointHistory);

        //가격 반영
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException());
        Integer prePoint = member.getPoint();
        member.setPoint(prePoint + point);
    }
    @Transactional
    public void alarmHubCharge(String memberIdStr, String mongIdStr, String thingsCode){
        Long memberId = Long.parseLong(memberIdStr);
        if(mongIdStr.equals("") || mongIdStr == null) return;
        Long mongId = Long.parseLong(mongIdStr);

        //충전 모션 요청 보내기


        ThingsHistory prevThingsHistory = thingsHistoryRepository.findTopByMemberIdAndThingsCodeOrderByThingsHistoryIdDesc(memberId, thingsCode);

        //2시간에 한번만
        LocalDateTime twoHourAgo = LocalDateTime.now().minusHours(2);
        if (!(prevThingsHistory == null || prevThingsHistory.getRegDt().isBefore(twoHourAgo)))
            return;
        ThingsHistory thingsHistory = ThingsHistory.builder()
                .memberId(memberId)
                .thingsCode(thingsCode)
                .build();
        thingsHistoryRepository.save(thingsHistory);

        //보너스 적립
        String action = "스마트싱스 허브충전 보너스";
        Integer point = 100;
        PointHistory pointHistory = PointHistory.builder()
                .point(point)
                .action(action)
                .memberId(memberId)
                .code(thingsCode)
                .build();
        paypointRepository.save(pointHistory);

        //가격 반영
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException());
        Integer prePoint = member.getPoint();
        member.setPoint(prePoint + point);
    }

    @Transactional
    public void alarmOpenDoor(String memberIdStr, String mongIdStr, String thingsCode){
        Long memberId = Long.parseLong(memberIdStr);
        if(mongIdStr.equals("") || mongIdStr == null) return;
        Long mongId = Long.parseLong(mongIdStr);

        //문열림 모션 요청 보내기


        ThingsHistory prevThingsHistory = thingsHistoryRepository.findTopByMemberIdAndThingsCodeOrderByThingsHistoryIdDesc(memberId, thingsCode);

        //1시간에 한번만
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(2);
        if (!(prevThingsHistory == null || prevThingsHistory.getRegDt().isBefore(oneHourAgo)))
            return;
        ThingsHistory thingsHistory = ThingsHistory.builder()
                .memberId(memberId)
                .thingsCode(thingsCode)
                .build();
        thingsHistoryRepository.save(thingsHistory);

        //보너스 적립
        String action = "스마트싱스 문열림센서 보너스";
        Integer point = 75;
        PointHistory pointHistory = PointHistory.builder()
                .point(point)
                .action(action)
                .memberId(memberId)
                .code(thingsCode)
                .build();
        paypointRepository.save(pointHistory);

        //가격 반영
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException());
        Integer prePoint = member.getPoint();
        member.setPoint(prePoint + point);
    }


}
