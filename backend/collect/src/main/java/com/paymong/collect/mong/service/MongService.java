package com.paymong.collect.mong.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.collect.global.client.CommonServiceClient;
import com.paymong.collect.global.code.GroupStateCode;
import com.paymong.collect.global.exception.GatewayException;
import com.paymong.collect.global.exception.NotFoundException;
import com.paymong.collect.global.vo.request.FindAllCommonCodeReqVo;
import com.paymong.collect.global.vo.response.FindAllCommonCodeResVo;
import com.paymong.collect.mong.dto.response.FindAllMongCollectResDto;
import com.paymong.collect.mong.dto.response.MongDto;
import com.paymong.collect.mong.entity.MongCollect;
import com.paymong.collect.mong.repository.MongCollectRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MongService {

    private final MongCollectRepository mongCollectRepository;

    private final CommonServiceClient commonServiceClient;

    @Transactional
    public FindAllMongCollectResDto findAllMongCollect(Long memberId)
        throws RuntimeException {
        FindAllMongCollectResDto findAllMongCollectResDto = FindAllMongCollectResDto.builder()
            .build();
        List<MongDto> eggs = new ArrayList<>();
        List<MongDto> level1 = new ArrayList<>();
        List<MongDto> level2 = new ArrayList<>();
        List<MongDto> level3 = new ArrayList<>();
        ObjectMapper om = new ObjectMapper();

        FindAllCommonCodeResVo findAllCommonCodeResVo;
        try {
            findAllCommonCodeResVo = om.convertValue(
                commonServiceClient.findAllCommonCode(
                        new FindAllCommonCodeReqVo(GroupStateCode.CHARACTER))
                    .getBody(), FindAllCommonCodeResVo.class);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new GatewayException();
        }

        List<MongDto> mongDtoList = findAllCommonCodeResVo.getCommonCodeList().stream()
            .map(MongDto::of)
            .collect(Collectors.toList());

        List<MongCollect> mongCollectList =
            mongCollectRepository.findByMemberIdOrderByMongCodeDesc(memberId);

        List<String> mongCollectCodeList = mongCollectList.stream()
            .map(MongCollect::getMongCode).collect(
                Collectors.toList());

        mongDtoList.forEach(e -> {
            char growth = e.getCharacterCode().charAt(2);
            switch (growth) {
                case '0':
                    eggs.add(e);
                    break;
                case '1':
                    level1.add(e);
                    break;
                case '2':
                    level2.add(e);
                    break;
                case '3':
                    level3.add(e);
                    break;
            }
        });

        eggs.stream()
            .map(e -> e.isContain(mongCollectCodeList))
            .collect(Collectors.toList());

        level1.stream()
            .map(e -> e.isContain(mongCollectCodeList))
            .collect(Collectors.toList());

        level2.stream()
            .map(e -> e.isContain(mongCollectCodeList))
            .collect(Collectors.toList());

        level3.stream()
            .map(e -> e.isContain(mongCollectCodeList))
            .collect(Collectors.toList());

        findAllMongCollectResDto.setEggs(eggs);
        findAllMongCollectResDto.setLevel1(level1);
        findAllMongCollectResDto.setLevel2(level2);
        findAllMongCollectResDto.setLevel3(level3);

        return findAllMongCollectResDto;
    }

    @Transactional
    public void findMong(Long memberId, String code) {
        mongCollectRepository.findByMemberIdAndMongCode(memberId, code)
            .orElseThrow(() -> new NotFoundException());
    }

    @Transactional
    public void addMong(Long memberId, String code) {
        mongCollectRepository.save(MongCollect.builder().mongCode(code).memberId(memberId).build());
    }
}
