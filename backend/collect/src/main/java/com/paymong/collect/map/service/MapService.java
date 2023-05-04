package com.paymong.collect.map.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.collect.global.client.CommonServiceClient;
import com.paymong.collect.global.code.GroupStateCode;
import com.paymong.collect.global.exception.CommonCodeException;
import com.paymong.collect.global.exception.NotFoundException;
import com.paymong.collect.global.vo.request.FindAllCommonCodeReqVo;
import com.paymong.collect.global.vo.response.CommonCodeDto;
import com.paymong.collect.global.vo.response.FindAllCommonCodeResVo;
import com.paymong.collect.map.dto.response.FindAllMapCollectResDto;
import com.paymong.collect.map.entity.MapCollect;
import com.paymong.collect.map.repository.MapCollectRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapService {

    private final MapCollectRepository mapCollectRepository;

    private final CommonServiceClient commonServiceClient;

    @Transactional
    public List<FindAllMapCollectResDto> findAllMapCollect(Long memberId) throws RuntimeException {

        List<CommonCodeDto> commonCodeDtoList;
        try {
            ObjectMapper om = new ObjectMapper();
            FindAllCommonCodeResVo findAllCommonCodeResVo = om.convertValue(
                commonServiceClient.findAllCommonCode(
                    new FindAllCommonCodeReqVo(GroupStateCode.MAP)).getBody(),
                FindAllCommonCodeResVo.class);

            log.info(findAllCommonCodeResVo.getCommonCodeDtoList().get(0).toString());
            commonCodeDtoList = findAllCommonCodeResVo.getCommonCodeDtoList();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CommonCodeException();
        }

        List<FindAllMapCollectResDto> findAllMapCollectResDtoList = commonCodeDtoList.stream()
            .filter(FindAllMapCollectResDto::isVaildMapCode)
            .map(FindAllMapCollectResDto::of)
            .collect(Collectors.toList());

        List<MapCollect> mapCollectList =
            mapCollectRepository.findAllByMemberIdOrderByMapCodeDesc(memberId);

        List<String> mapCollectCodeList = mapCollectList.stream()
            .map(MapCollect::getMapCode).collect(Collectors.toList());

        findAllMapCollectResDtoList = findAllMapCollectResDtoList.stream()
            .map(e -> e.isContain(mapCollectCodeList))
            .collect(Collectors.toList());

        return findAllMapCollectResDtoList;
    }

    @Transactional
    public void findMap(Long memberId, String code) throws RuntimeException {
        mapCollectRepository.findByMemberIdAndMapCode(memberId, code)
            .orElseThrow(() -> new NotFoundException());
    }

    @Transactional
    public void addMap(Long memberId, String code) throws RuntimeException {
        mapCollectRepository.save(MapCollect.builder().mapCode(code).memberId(memberId).build());
    }
}
