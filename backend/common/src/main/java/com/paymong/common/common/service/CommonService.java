package com.paymong.common.common.service;

import com.paymong.common.common.dto.request.FindAllCommonCodeReqDto;
import com.paymong.common.common.dto.request.FindCommonCodeReqDto;
import com.paymong.common.common.dto.response.FindAllCommonCodeResDto;
import com.paymong.common.common.dto.response.FindCommonCodResDto;
import com.paymong.common.common.dto.response.FindEggResDto;
import com.paymong.common.common.entity.CommonCode;
import com.paymong.common.common.entity.GroupCode;
import com.paymong.common.common.repository.CommonCodeRepository;
import com.paymong.common.common.repository.GroupCodeRepository;
import com.paymong.common.global.exception.NotFoundException;
import java.util.List;
import java.util.Random;
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
    public FindEggResDto findRandomEgg() throws RuntimeException {
        List<CommonCode> commonCodeList = commonCodeRepository.findByCodeStartsWith("CH0")
            .orElseThrow();
        Random random = new Random(); //랜덤 객체 생성(디폴트 시드값 : 현재시간)
        random.setSeed(System.currentTimeMillis()); //시드값 설정을 따로 할수도 있음
        return FindEggResDto.of(commonCodeList.get(random.nextInt(commonCodeList.size())));
    }

}
