package com.paymong.collect.collect.service;

import com.paymong.collect.collect.dto.response.FindAllMapCollectResDto;
import com.paymong.collect.collect.dto.response.FindAllMongCollectResDto;
import com.paymong.collect.collect.dto.response.MongDto;
import com.paymong.collect.collect.entity.MapCollect;
import com.paymong.collect.collect.entity.MongCollect;
import com.paymong.collect.collect.repository.MapCollectRepository;
import com.paymong.collect.collect.repository.MongCollectRepository;
import com.paymong.collect.global.vo.response.CommonCodeResVo;
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


    @Transactional
    public List<FindAllMapCollectResDto> findAllMapCollect(String memberId)
        throws RuntimeException {

        List<FindAllMapCollectResDto> findAllMapCollectResDtoList =
            MapTest().stream()
                .filter(FindAllMapCollectResDto::isVaildMapCode)
                .map(FindAllMapCollectResDto::of)
                .collect(Collectors.toList());

        Optional<List<MapCollect>> mapCollectList =
            mapCollectRepository.findAllByMemberIdOrderByMapCodeDesc(Long.parseLong(memberId));

        if (mapCollectList.isPresent()) {
            List<String> mapCollectCodeList = mapCollectList.get().stream()
                .map(MapCollect::getMapCode).collect(Collectors.toList());

            findAllMapCollectResDtoList.stream()
                .map(e -> e.isContain(mapCollectCodeList));

        }

        return findAllMapCollectResDtoList;
    }

    public List<CommonCodeResVo> MapTest() {
        List<CommonCodeResVo> CommonCodeResVoList = new ArrayList<>();
        CommonCodeResVoList.add(new CommonCodeResVo("MP000", "메인", "MP", "맵"));
        CommonCodeResVoList.add(new CommonCodeResVo("MP001", "스타벅스", "MP", "맵"));
        CommonCodeResVoList.add(new CommonCodeResVo("MP002", "이디야", "MP", "맵"));
        CommonCodeResVoList.add(new CommonCodeResVo("MP003", "할리스", "MP", "맵"));
        return CommonCodeResVoList;
    }

    public List<CommonCodeResVo> MongTest() {
        List<CommonCodeResVo> CommonCodeResVoList = new ArrayList<>();
        CommonCodeResVoList.add(new CommonCodeResVo("CH000", "화산알", "CH", "캐릭터"));
        CommonCodeResVoList.add(new CommonCodeResVo("CH001", "석탄알", "CH", "캐릭터"));
        CommonCodeResVoList.add(new CommonCodeResVo("CH002", "황금알", "CH", "캐릭터"));
        CommonCodeResVoList.add(new CommonCodeResVo("CH003", "목성알", "CH", "캐릭터"));
        CommonCodeResVoList.add(new CommonCodeResVo("CH004", "지구알", "CH", "캐릭터"));
        CommonCodeResVoList.add(new CommonCodeResVo("CH005", "바람알", "CH", "캐릭터"));
        CommonCodeResVoList.add(new CommonCodeResVo("CH100", "별몽", "CH", "캐릭터"));
        CommonCodeResVoList.add(new CommonCodeResVo("CH102", "둥글몽", "CH", "캐릭터"));
        CommonCodeResVoList.add(new CommonCodeResVo("CH103", "나네몽", "CH", "캐릭터"));
        CommonCodeResVoList.add(new CommonCodeResVo("CH200", "까몽", "CH", "캐릭터"));
        CommonCodeResVoList.add(new CommonCodeResVo("CH210", "열나는별별몽", "CH", "캐릭터"));
        return CommonCodeResVoList;
    }

    @Transactional
    public List<FindAllMongCollectResDto> findAllMongCollect(String memberId)
        throws RuntimeException {
        List<FindAllMongCollectResDto> findAllMapCollectResDtoList = new ArrayList<>();
        List<MongDto> eggs = new ArrayList<>();
        List<MongDto> level1 = new ArrayList<>();
        List<MongDto> level2 = new ArrayList<>();
        List<MongDto> level3 = new ArrayList<>();

        List<MongDto> mongDtoList = MongTest().stream().map(MongDto::of)
            .collect(Collectors.toList());



        Optional<List<MongCollect>> mongCollectList =
            mongCollectRepository.findByMemberIdOrderByMongCodeDesc(Long.parseLong(memberId));

//        if (mongCollectList.isPresent()) {
//            mongDtoList.stream().map(e -> e.isValid(mongCollectList));
//        }

        MongTest().stream().map(e -> {
            MongDto mongDto = MongDto.of(e);
            char growth = mongDto.getCharacterCode().charAt(2);
            switch (growth) {
                case 0:
                    eggs.add(mongDto);
                    break;
                case 1:
                    level1.add(mongDto);
                    break;
                case 2:
                    level2.add(mongDto);
                    break;
                case 3:
                    level3.add(mongDto);
                    break;
            }
            return 0;
        });

//        findAllMapCollectResDtoList.add(eggs);
//        findAllMapCollectResDtoList.add(level1);
//        findAllMapCollectResDtoList.add(level2);
//        findAllMapCollectResDtoList.add(level3);

        return null;
    }

}
