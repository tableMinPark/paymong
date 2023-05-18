package com.paymong.collect.mong.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.collect.global.client.CommonServiceClient;
import com.paymong.collect.global.code.GroupStateCode;
import com.paymong.collect.global.exception.CommonCodeException;
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
    public List<MongDto> findAllMongCollect(Long memberId)
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
            throw new CommonCodeException();
        }

        List<MongDto> mongDtoList = findAllCommonCodeResVo.getCommonCodeDtoList().stream()
            .map(MongDto::of)
            .collect(Collectors.toList());

        List<MongCollect> mongCollectList =
            mongCollectRepository.findByMemberIdOrderByMongCodeDesc(memberId);

        List<String> mongCollectCodeList = mongCollectList.stream()
            .map(MongCollect::getMongCode).collect(
                Collectors.toList());

        mongDtoList.stream()
            .map(e -> e.isContain(mongCollectCodeList))
            .collect(Collectors.toList());

        return mongDtoList;
    }

    @Transactional
    public void findMong(Long memberId, String code) throws RuntimeException {
        mongCollectRepository.findByMemberIdAndMongCode(memberId, code)
            .orElseThrow(() -> new NotFoundException());
    }

    @Transactional
    public void addMong(Long memberId, String code) throws RuntimeException {
        mongCollectRepository.save(MongCollect.builder().mongCode(code).memberId(memberId).build());
    }
}
