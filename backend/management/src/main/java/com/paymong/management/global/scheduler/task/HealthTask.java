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
public class HealthTask {
    private final MongRepository mongRepository;
    private final WebSocketService webSocketService;
    @Transactional
    public Boolean reduceHealth(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
                .orElseThrow(() -> new NotFoundMongException());

        Integer poop = mong.getPoopCount();
        Integer health = mong.getHealth();
        if(poop == 0){
            health = health - 1 < 0 ? 0 : health - 1;
            mong.setHealth(health);
        }else{
            health = health - (poop*poop) < 0 ? 0 : health - (poop*poop);
            mong.setHealth(health);
        }
        if(health == 0){
            mong.setStateCode(MongConditionCode.SICK.getCode());
            log.info("{}의 죽음의 카운트가 시작됩니다.", mongId);

            webSocketService.sendStatus(mong, WebSocketCode.DEATH);
            return false;
        }
        log.info("{}의 체력이 감소하였습니다.", mongId);
        webSocketService.sendStatus(mong, WebSocketCode.SUCCESS);
        return true;
    }
}
