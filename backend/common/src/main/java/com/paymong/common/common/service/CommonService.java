package com.paymong.common.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.paymong.common.common.dto.request.FindAllCommonCodeReqDto;
import com.paymong.common.common.dto.request.FindCommonCodeReqDto;
import com.paymong.common.common.dto.request.FindLastBuyReqDto;
import com.paymong.common.common.dto.request.FindRandomMongReqDto;
import com.paymong.common.common.dto.response.CommonCodeDto;
import com.paymong.common.common.dto.response.FindLastBuyResDto;
import com.paymong.common.common.dto.response.Food;
import com.paymong.common.common.entity.CommonCode;
import com.paymong.common.common.entity.GroupCode;
import com.paymong.common.common.repository.CommonCodeRepository;
import com.paymong.common.common.repository.GroupCodeRepository;
import com.paymong.common.global.client.InformationServiceClient;
import com.paymong.common.global.code.GroupStateCode;
import com.paymong.common.global.exception.InformationException;
import com.paymong.common.global.exception.NotFoundException;
import com.paymong.common.status.entitiy.Status;
import com.paymong.common.status.repository.StatusRepository;
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

    private final InformationServiceClient informationServiceClient;

    private final StatusRepository statusRepository;

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
    public List<Food> findAllFood(String foodCategory, String mongId)
        throws RuntimeException {

        GroupCode groupCode = groupCodeRepository.findByCode(foodCategory.replace("\"", ""))
            .orElseThrow(() -> new NotFoundException());

        log.info("foodCategory - {}", foodCategory);

        List<CommonCode> commonCodeList = commonCodeRepository.findAllByGroupCode(groupCode);

        List<Food> findAllFoodResDto =
            commonCodeList.stream().map(e -> {
                FindLastBuyResDto findLastBuyResDto;
                try {
                    ObjectMapper om = new ObjectMapper();
                    om.registerModule(new JavaTimeModule());
                    om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    findLastBuyResDto = om.convertValue(
                        informationServiceClient.findLastBuy(mongId,
                                new FindLastBuyReqDto(e.getCode()))
                            .getBody(), FindLastBuyResDto.class);
                    int price;
                    if (foodCategory.equals(GroupStateCode.FOOD.getCode())) {
                        String codeStr = "AT0" + e.getCode().charAt(3) + "0";
                        Status status = statusRepository.findByCode(codeStr).orElseThrow();
                        price = status.getPoint() * -1;
                    } else {
                        String codeStr = "AT0" + e.getCode().charAt(3) + "1";
                        Status status = statusRepository.findByCode(codeStr).orElseThrow();
                        price = status.getPoint() * -1;
                    }
                    return Food.of(e, price, findLastBuyResDto.getLastBuy());
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                    throw new InformationException();
                }
            }).collect(Collectors.toList());

        return findAllFoodResDto;
    }

    @Transactional
    public CommonCodeDto findCodeByName(String name) throws RuntimeException {
        CommonCode commonCode = commonCodeRepository.findByName(name)
            .orElseThrow(() -> new NotFoundException());
        return CommonCodeDto.of(commonCode);
    }

    @Transactional
    public CommonCodeDto findRandomMong(FindRandomMongReqDto findRandomMongReqDto) {
        List<CommonCode> commonCodeList;
        if (findRandomMongReqDto.getLevel() == 0) {

            commonCodeList = commonCodeRepository.findByCodeStartsWith("CH0");
            Random random = new Random(); //랜덤 객체 생성(디폴트 시드값 : 현재시간)
            random.setSeed(System.currentTimeMillis()); //시드값 설정을 따로 할수도 있음

            return CommonCodeDto.of(commonCodeList.get(random.nextInt(commonCodeList.size())));
        } else if (findRandomMongReqDto.getLevel() == 1) {

            commonCodeList = commonCodeRepository.findByCodeStartsWith("CH1");
            Random random = new Random(); //랜덤 객체 생성(디폴트 시드값 : 현재시간)
            random.setSeed(System.currentTimeMillis()); //시드값 설정을 따로 할수도 있음

            return CommonCodeDto.of(commonCodeList.get(random.nextInt(commonCodeList.size())));
        } else if (findRandomMongReqDto.getLevel() == 2) {
            String code =
                "CH2" + findRandomMongReqDto.getTier() + findRandomMongReqDto.getType().toString();

            CommonCode commonCode = commonCodeRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException());

            return CommonCodeDto.of(commonCode);
        } else {
            String code = "CH3" + findRandomMongReqDto.getTier() + findRandomMongReqDto.getType();

            CommonCode commonCode = commonCodeRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException());

            return CommonCodeDto.of(commonCode);

        }


    }

}
