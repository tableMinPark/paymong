package com.paymong.member.things.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.member.global.client.CommonServiceClient;
import com.paymong.member.things.dto.request.AddThingsReqDto;
import com.paymong.member.things.dto.request.FindAddableThingsReqDto;
import com.paymong.member.things.dto.request.RemoveThingsReqDto;
import com.paymong.member.things.dto.response.FindAddableThingsResDto;
import com.paymong.member.things.dto.response.FindThingsListResDto;
import com.paymong.member.things.dto.response.ThingsCommonCode;
import com.paymong.member.things.dto.response.ThingsCommonCodeList;
import com.paymong.member.things.entity.Things;
import com.paymong.member.things.repository.ThingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThingsService {
    private final ThingsRepository thingsRepository;
    private final CommonServiceClient commonServiceClient;

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
        for(Things things : myThings) check.put(things.getThingsCode(),1);

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
        Things added = Things.builder()
                .memberId(memberId)
                .thingsCode(addThingsReqDto.getThingsCode())
                .thingsName(addThingsReqDto.getThingsName())
                .routine(addThingsReqDto.getRoutine())
                .build();
        thingsRepository.save(added);
        return  added;
    }


    public void removeThings(String memberIdStr, RemoveThingsReqDto removeThingsReqDto) throws Exception{
        Long memberId = Long.parseLong(memberIdStr);
        Long thingsId = removeThingsReqDto.getThingsId();
        thingsRepository.deleteByMemberIdAndThingsId(memberId, thingsId);
    }



}
