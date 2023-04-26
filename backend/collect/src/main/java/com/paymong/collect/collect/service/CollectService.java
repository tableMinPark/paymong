package com.paymong.collect.collect.service;

import com.paymong.collect.collect.dto.response.FindAllMapCollectResDto;
import com.paymong.collect.collect.dto.response.FindAllMongCollectResDto;
import com.paymong.collect.collect.entity.MapCollect;
import com.paymong.collect.collect.entity.MongCollect;
import com.paymong.collect.collect.repository.MapCollectRepository;
import com.paymong.collect.collect.repository.MongCollectRepository;
import com.paymong.collect.global.client.CommonServiceClient;
import java.util.List;
import java.util.Optional;
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
        Optional<List<MapCollect>> mapCollectList = Optional.ofNullable(
            mapCollectRepository.findByMemberIdOrderByMapCodeDesc(Long.parseLong(memberId)));
        // common에서 코드 받아서 코드 리스트 만들기
        // 코드 리스트 만큼 맵 돌며서 mapCollectList의 mapCode와 코드 리스트의 code가 일치하면 -> true / 일치 X면 -> false만들기
//        commonServiceClient.findAllMapCode().stream().map()

//        List<String> commonList = null;
//        List<FindAllMapCollectResDto> productNameList = commonList.stream()
//            .map(String::getProductName)
//            .collect(Collectors.toList());

        return null;
    }

    @Transactional
    public List<FindAllMongCollectResDto> findAllMongCollect(String memberId)
        throws RuntimeException {
        Optional<List<MongCollect>> mongCollectList = Optional.ofNullable(
            mongCollectRepository.findByMemberIdOrderBMongCodeDesc(Long.parseLong(memberId)));
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
