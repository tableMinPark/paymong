package com.paymong.common.status.service;

import com.paymong.common.global.exception.NotFoundException;
import com.paymong.common.status.dto.request.FindStatusReqDto;
import com.paymong.common.status.dto.response.FindStatusResDto;
import com.paymong.common.status.entitiy.Status;
import com.paymong.common.status.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    @Transactional
    public FindStatusResDto findStatus(FindStatusReqDto findStatusReqDto) {
        Status status = statusRepository.findByCode(findStatusReqDto.getCode().replace("\"", ""))
            .orElseThrow(() -> new NotFoundException());

        return FindStatusResDto.of(status);
    }
}
