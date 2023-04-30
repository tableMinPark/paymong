package com.paymong.collect.collect.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.collect.collect.dto.response.FindAllMapCollectResDto;
import com.paymong.collect.collect.dto.response.FindAllMongCollectResDto;
import com.paymong.collect.collect.dto.response.MongDto;
import com.paymong.collect.collect.entity.MapCollect;
import com.paymong.collect.collect.entity.MongCollect;
import com.paymong.collect.collect.repository.MapCollectRepository;
import com.paymong.collect.collect.repository.MongCollectRepository;
import com.paymong.collect.global.vo.request.FindAllCommonCodeReqVo;
import com.paymong.collect.global.vo.response.FindAllCommonCodeResVo;
import com.paymong.collect.global.client.CommonServiceClient;
import com.paymong.collect.global.code.GroupStateCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectService {

    private final MapCollectRepository mapCollectRepository;

    private final MongCollectRepository mongCollectRepository;

    private final CommonServiceClient commonServiceClient;


    @Transactional
    public List<FindAllMapCollectResDto> findAllMapCollect(String memberId)
        throws RuntimeException {

        ObjectMapper om = new ObjectMapper();

        FindAllCommonCodeResVo findAllCommonCodeResVo = om.convertValue(
            commonServiceClient.findAllCommonCode(new FindAllCommonCodeReqVo(GroupStateCode.MAP))
                .getBody(), FindAllCommonCodeResVo.class);

        if (findAllCommonCodeResVo.getCommonCodeList().isEmpty()) {
            throw new RuntimeException();
        }

        List<FindAllMapCollectResDto> findAllMapCollectResDtoList =
            findAllCommonCodeResVo.getCommonCodeList().stream()
                .filter(FindAllMapCollectResDto::isVaildMapCode)
                .map(FindAllMapCollectResDto::of)
                .collect(Collectors.toList());

        Optional<List<MapCollect>> mapCollectList =
            mapCollectRepository.findAllByMemberIdOrderByMapCodeDesc(Long.parseLong(memberId));

        if (mapCollectList.isPresent() && mapCollectList.get().size() != 0) {
            List<String> mapCollectCodeList = mapCollectList.get().stream()
                .map(MapCollect::getMapCode).collect(Collectors.toList());

            findAllMapCollectResDtoList = findAllMapCollectResDtoList.stream()
                .map(e -> e.isContain(mapCollectCodeList))
                .collect(Collectors.toList());
        }

        return findAllMapCollectResDtoList;
    }

    @Transactional
    public FindAllMongCollectResDto findAllMongCollect(String memberId)
        throws RuntimeException {
        FindAllMongCollectResDto findAllMongCollectResDto = FindAllMongCollectResDto.builder()
            .build();
        List<MongDto> eggs = new ArrayList<>();
        List<MongDto> level1 = new ArrayList<>();
        List<MongDto> level2 = new ArrayList<>();
        List<MongDto> level3 = new ArrayList<>();

        ObjectMapper om = new ObjectMapper();

        FindAllCommonCodeResVo findAllCommonCodeResVo = om.convertValue(
            commonServiceClient.findAllCommonCode(new FindAllCommonCodeReqVo(GroupStateCode.CHARACTER))
                .getBody(), FindAllCommonCodeResVo.class);

        List<MongDto> mongDtoList = findAllCommonCodeResVo.getCommonCodeList().stream()
            .map(MongDto::of)
            .collect(Collectors.toList());

        Optional<List<MongCollect>> mongCollectList =
            mongCollectRepository.findByMemberIdOrderByMongCodeDesc(Long.parseLong(memberId));

        if (mongCollectList.isPresent() && mongCollectList.get().size() != 0) {
            List<String> mongCollectCodeList = mongCollectList.get().stream()
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

        }

        findAllMongCollectResDto.setEggs(eggs);
        findAllMongCollectResDto.setLevel1(level1);
        findAllMongCollectResDto.setLevel2(level2);
        findAllMongCollectResDto.setLevel3(level3);

        return findAllMongCollectResDto;
    }

}
