package com.paymong.management.poop.service;

import com.paymong.management.global.code.MongActiveCode;
import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.code.WebSocketCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.socket.service.WebSocketService;
import com.paymong.management.history.entity.ActiveHistory;
import com.paymong.management.history.repository.ActiveHistoryRepository;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.poop.vo.PoopMongReqVo;
import com.paymong.management.status.dto.MongStatusDto;
import com.paymong.management.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PoopService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoopService.class);
    private final MongRepository mongRepository;
    private final ActiveHistoryRepository activeHistoryRepository;
    private final StatusService statusService;
    private final WebSocketService webSocketService;

    int cnt = 0;
    @Transactional
    public MongStatusDto removePoop(PoopMongReqVo poopMongReqVo) throws Exception{
        Mong mong = mongRepository.findByMongId(poopMongReqVo.getMongId())
                .orElseThrow(() -> new NotFoundMongException());

        mong.setPoopCount(0);

        // 상태 체크
        MongConditionCode conditionCode = statusService.checkCondition(mong);
        mong.setStateCode(conditionCode.getCode());

        ActiveHistory activeHistory = ActiveHistory.builder()
                .activeCode(MongActiveCode.CLEAN.getCode())
                .activeTime(LocalDateTime.now())
                .mongId(poopMongReqVo.getMongId())
                .build();

        activeHistoryRepository.save(activeHistory);

        webSocketService.sendStatus(mong, WebSocketCode.SUCCESS);
        MongStatusDto mongStatusDto = new MongStatusDto(mong, WebSocketCode.SUCCESS);
        return mongStatusDto;
    }

}
