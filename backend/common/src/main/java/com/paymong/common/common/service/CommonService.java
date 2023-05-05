package com.paymong.common.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.common.common.dto.request.FindAllCommonCodeReqDto;
import com.paymong.common.common.dto.request.FindCommonCodeReqDto;
import com.paymong.common.common.dto.response.CommonCodeDto;
import com.paymong.common.common.dto.response.FindAllFoodResDto;
import com.paymong.common.common.dto.response.Food;
import com.paymong.common.common.entity.CommonCode;
import com.paymong.common.common.entity.GroupCode;
import com.paymong.common.common.repository.CommonCodeRepository;
import com.paymong.common.common.repository.GroupCodeRepository;
import com.paymong.common.global.client.ManagementServiceClient;
import com.paymong.common.global.exception.NotFoundException;
import com.paymong.common.global.vo.request.FindLastBuyReqVo;
import com.paymong.common.global.vo.response.FindLastBuyResVo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
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

    private final ManagementServiceClient managementServiceClient;

    @Transactional
    public List<CommonCodeDto> findAllCommonCode(
        FindAllCommonCodeReqDto findAllCommonCodeReqDto) throws RuntimeException {

        GroupCode groupCode = groupCodeRepository.findById(
                findAllCommonCodeReqDto.getGroupCode().replace("\"", ""))
            .orElseThrow(() -> new NotFoundException());

        List<CommonCodeDto> commonCodeList = commonCodeRepository.findAllByGroupCode(groupCode)
            .stream()
            .map(CommonCodeDto::of).collect(Collectors.toList());

        return commonCodeList;
    }

    @Transactional
    public CommonCodeDto findCommonCode(FindCommonCodeReqDto findCommonCodeReqDto)
        throws RuntimeException {

        CommonCode commonCode = commonCodeRepository.findById(
                findCommonCodeReqDto.getCode().replace("\"", ""))
            .orElseThrow(() -> new NotFoundException());

        return CommonCodeDto.of(commonCode);
    }

    @Transactional
    public FindAllFoodResDto findAllFood(String mongId, String foodCategory)
        throws RuntimeException {

        GroupCode groupCode = groupCodeRepository.findById(foodCategory.replace("\"", ""))
            .orElseThrow(() -> new NotFoundException());

        log.info("foodCategory - {}", foodCategory);

        List<CommonCode> commonCodeList = commonCodeRepository.findAllByGroupCode(groupCode);

        FindAllFoodResDto findAllFoodResDto = new FindAllFoodResDto(
            commonCodeList.stream().map(e -> {

                ObjectMapper om = new ObjectMapper();
                FindLastBuyResVo findLastBuyResVo = om.convertValue(
                    managementServiceClient.findLastBuy(mongId, new FindLastBuyReqVo(foodCategory))
                        .getBody(), FindLastBuyResVo.class);

//                FindLastBuyResVo findLastBuyResVo = FindLastBuyResVo.builder().lastBuy(
//                    LocalDateTime.now()).build();

                return Food.of(e, findLastBuyResVo.getLastBuy());
            }).collect(Collectors.toList()));

        return findAllFoodResDto;
    }

    @Transactional
    public CommonCodeDto findRandomEgg() throws RuntimeException {
        List<CommonCode> commonCodeList = commonCodeRepository.findByCodeStartsWith("CH0");
        Random random = new Random(); //랜덤 객체 생성(디폴트 시드값 : 현재시간)
        random.setSeed(System.currentTimeMillis()); //시드값 설정을 따로 할수도 있음
        return CommonCodeDto.of(commonCodeList.get(random.nextInt(commonCodeList.size())));
    }

    @Transactional
    public CommonCodeDto findCodeByName(String name) throws RuntimeException {
        CommonCode commonCode = commonCodeRepository.findByName(name)
            .orElseThrow(() -> new NotFoundException());
        return CommonCodeDto.of(commonCode);
    }

}
