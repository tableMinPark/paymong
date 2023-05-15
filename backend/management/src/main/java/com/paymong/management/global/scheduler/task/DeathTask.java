package com.paymong.management.global.scheduler.task;

import com.paymong.management.global.code.MongActiveCode;
import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.code.WebSocketCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.socket.service.WebSocketService;
import com.paymong.management.history.entity.ActiveHistory;
import com.paymong.management.history.repository.ActiveHistoryRepository;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeathTask {

    private final MongRepository mongRepository;
    private final WebSocketService webSocketService;
    private final ActiveHistoryRepository activeHistoryRepository;

    @Transactional
    public void deathMong(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
                .orElseThrow(() -> new NotFoundMongException());
        mong.setStateCode(MongConditionCode.DIE.getCode());

        ActiveHistory activeHistory = ActiveHistory.builder()
                .activeCode(MongConditionCode.DIE.getCode())
                .activeTime(LocalDateTime.now())
                .mongId(mongId)
                .build();

        activeHistoryRepository.save(activeHistory);

        log.info("{} 의 mong이 죽었습니다.", mongId);
        webSocketService.sendStatus(mong, WebSocketCode.DEATH);
    }
}
