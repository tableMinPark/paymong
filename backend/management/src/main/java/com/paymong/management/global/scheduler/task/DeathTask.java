package com.paymong.management.global.scheduler.task;

import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.code.WebSocketCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.socket.service.WebSocketService;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeathTask {

    private final MongRepository mongRepository;
    private final WebSocketService webSocketService;
    @Transactional
    public void deathMong(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
                .orElseThrow(() -> new NotFoundMongException());
        mong.setStateCode(MongConditionCode.DIE.getCode());
        log.info("{} 의 mong이 죽었습니다.", mongId);
        webSocketService.sendStatus(mong, WebSocketCode.DEATH);
    }
}
