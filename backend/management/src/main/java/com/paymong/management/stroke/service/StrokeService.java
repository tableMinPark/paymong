package com.paymong.management.stroke.service;

import com.paymong.management.global.code.MongActiveCode;
import com.paymong.management.global.code.WebSocketCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.scheduler.StrokeScheduler;
import com.paymong.management.history.entity.ActiveHistory;
import com.paymong.management.history.repository.ActiveHistoryRepository;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.status.dto.MongStatusDto;
import com.paymong.management.stroke.vo.StrokeMongReqVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StrokeService {
    private final MongRepository mongRepository;
    private final StrokeScheduler strokeScheduler;
    private final ActiveHistoryRepository activeHistoryRepository;
    @Transactional
    public MongStatusDto strokeMong(StrokeMongReqVo strokeMongReqVo) throws Exception{

        if(strokeScheduler.checkMong(strokeMongReqVo.getMongId())){
            MongStatusDto mongStatusDto = new MongStatusDto(WebSocketCode.FAIL);
            return mongStatusDto;
        }

        Mong mong = mongRepository.findByMongId(strokeMongReqVo.getMongId())
                .orElseThrow(() -> new NotFoundMongException());
        mong.setStrokeCount(mong.getStrokeCount() + 1);

        ActiveHistory activeHistory = ActiveHistory.builder()
                .activeCode(MongActiveCode.STROKE.getCode())
                .activeTime(LocalDateTime.now())
                .mongId(strokeMongReqVo.getMongId())
                .build();

        activeHistoryRepository.save(activeHistory);

        strokeScheduler.startScheduler(strokeMongReqVo.getMongId());
        MongStatusDto mongStatusDto = new MongStatusDto(WebSocketCode.SUCCESS);

        return mongStatusDto;
    }
}
