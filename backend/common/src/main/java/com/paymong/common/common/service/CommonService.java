package com.paymong.common.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.common.common.dto.request.FindAllCommonCodeReqDto;
import com.paymong.common.common.dto.request.FindCommonCodeReqDto;
import com.paymong.common.common.dto.response.FindAllCommonCodeResDto;
import com.paymong.common.common.dto.response.FindAllFoodResDto;
import com.paymong.common.common.dto.response.FindCommonCodResDto;
import com.paymong.common.common.dto.response.Food;
import com.paymong.common.common.entity.CommonCode;
import com.paymong.common.common.entity.GroupCode;
import com.paymong.common.common.repository.CommonCodeRepository;
import com.paymong.common.common.repository.GroupCodeRepository;
import com.paymong.common.global.client.PayPointServiceClient;
import com.paymong.common.global.exception.NotFoundException;
import com.paymong.common.global.vo.request.FindLastBuyReqVo;
import com.paymong.common.global.vo.response.FindLastBuyResVo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {

    private final CommonCodeRepository commonCodeRepository;

    private final GroupCodeRepository groupCodeRepository;

    private final PayPointServiceClient payPointServiceClient;

    @Transactional
    public FindAllCommonCodeResDto findAllCommonCode(
        FindAllCommonCodeReqDto findAllCommonCodeReqDto) throws RuntimeException {
        GroupCode groupCode = groupCodeRepository.findById(
                findAllCommonCodeReqDto.getGroupCode().replace("\"", ""))
            .orElseThrow(() -> new NotFoundException());
        List<CommonCode> commonCodeList = commonCodeRepository.findAllByGroupCode(groupCode)
            .orElseThrow();

        return new FindAllCommonCodeResDto(commonCodeList);
    }

    @Transactional
    public FindCommonCodResDto findCommonCode(FindCommonCodeReqDto findCommonCodeReqDto)
        throws RuntimeException {
        CommonCode commonCode = commonCodeRepository.findById(
                findCommonCodeReqDto.getCode().replace("\"", ""))
            .orElseThrow(() -> new NotFoundException());

        return new FindCommonCodResDto(commonCode);
    }

    @Transactional
    public FindAllFoodResDto findAllFood(String foodCategory, String mongKey)
        throws RuntimeException {
        GroupCode groupCode = groupCodeRepository.findById(foodCategory.replace("\"", ""))
            .orElseThrow(() -> new NotFoundException());

        List<CommonCode> commonCodeList = commonCodeRepository.findAllByGroupCode(groupCode)
            .orElseThrow();

        FindAllFoodResDto findAllFoodResDto = new FindAllFoodResDto(
            commonCodeList.stream().map(e -> {
                ObjectMapper om = new ObjectMapper();
                FindLastBuyResVo findLastBuyResVo = om.convertValue(
                    payPointServiceClient.findLastBuy(new FindLastBuyReqVo(foodCategory, mongKey))
                        .getBody(), FindLastBuyResVo.class);
                return Food.of(e, findLastBuyResVo.getLastBuy());
            }).collect(Collectors.toList()));

        return findAllFoodResDto;
    }


}
