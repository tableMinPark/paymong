package com.paymong.collect.collect.service;

import com.paymong.collect.collect.dto.response.FindAllMapCollectResDto;
import com.paymong.collect.collect.dto.response.FindAllMongCollectResDto;
import com.paymong.collect.collect.entity.MapCollect;
import com.paymong.collect.collect.entity.MongCollect;
import com.paymong.collect.collect.repository.MapCollectRepository;
import com.paymong.collect.collect.repository.MongCollectRepository;
import com.paymong.collect.global.vo.response.MapCodeResVo;
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
                .map(FindAllMapCollectResDto::ofMapCode)
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

    public List<MapCodeResVo> MapTest() {
        List<MapCodeResVo> mapCodeResVoList = new ArrayList<>();
        mapCodeResVoList.add(new MapCodeResVo("MP000", "메인", "MP", "맵"));
        mapCodeResVoList.add(new MapCodeResVo("MP001", "스타벅스", "MP", "맵"));
        mapCodeResVoList.add(new MapCodeResVo("MP002", "이디야", "MP", "맵"));
        mapCodeResVoList.add(new MapCodeResVo("MP003", "할리스", "MP", "맵"));
        return mapCodeResVoList;
    }

    @Transactional
    public List<FindAllMongCollectResDto> findAllMongCollect(String memberId)
        throws RuntimeException {
        Optional<List<MongCollect>> mongCollectList = Optional.ofNullable(
            mongCollectRepository.findByMemberIdOrderByMongCodeDesc(Long.parseLong(memberId)));
        // common에서 코드 받아서 코드 리스트 만들기
        // 코드 리스트 만큼 맵 돌며서 mapCollectList와 Code가 일치하면 true 만들기

//        commonServiceClient.findAllMongCode().stream().map()
//        List<String> commonList = null;
//        List<FindAllMongCollectResDto> productNameList = commonList.stream()
//            .map(String::getProductName)
//            .collect(Collectors.toList());

        return null;
    }

}
