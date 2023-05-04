package com.paymong.member.things.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.member.global.client.CommonServiceClient;
import com.paymong.member.global.exception.NotFoundMapException;
import com.paymong.member.paypoint.dto.response.FindMapByNameResDto;
import com.paymong.member.things.dto.request.AddThingsReqDto;
import com.paymong.member.things.dto.request.RemoveThingsReqDto;
import com.paymong.member.things.dto.response.FindAddableThingsResDto;
import com.paymong.member.things.dto.response.FindThingsListResDto;
import com.paymong.member.things.dto.response.ThingsCommonCode;
import com.paymong.member.things.entity.Things;
import com.paymong.member.things.repository.ThingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Map<String, String> req = new HashMap<>();
        req.put("commonCode",groupCode);
        ResponseEntity<Object> response =  commonServiceClient.findAllCommonCode(req);
        if(response.getStatusCode()== HttpStatus.BAD_REQUEST) throw new NotFoundMapException();
        ObjectMapper om = new ObjectMapper();
        //List<ThingsCommonCode>  = om.convertValue(response.getBody(), ThingsCommonCode.class);

        return null;
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
