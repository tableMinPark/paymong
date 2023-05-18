package com.paymong.management.global.scheduler.task;

import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.code.WebSocketCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.exception.UnsuitableException;
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
public class SatietyTask {
    private final MongRepository mongRepository;
    private final WebSocketService webSocketService;
    @Transactional
    public Boolean reduceSatiety(Long mongId) throws NotFoundMongException, UnsuitableException {
        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
                .orElseThrow(() -> new NotFoundMongException());

        if(mong.getStateCode().equals(MongConditionCode.SLEEP.getCode()) ||
                mong.getStateCode().equals(MongConditionCode.DIE.getCode()) ||
                mong.getStateCode().equals(MongConditionCode.GRADUATE.getCode())){
            throw new UnsuitableException();
        }

        Integer poop = mong.getPoopCount();
        Integer satiety = mong.getSatiety();
        if(poop == 0){
            satiety = satiety - 1 < 0 ? 0 : satiety - 1;
            mong.setSatiety(satiety);
        }else{
            satiety = satiety - (poop*poop) < 0 ? 0 : satiety - (poop*poop);
            mong.setSatiety(satiety);
        }

        if(satiety < 5){
            if(mong.getStateCode().equals(MongConditionCode.NORMAL.getCode())){
                mong.setStateCode(MongConditionCode.HUNGRY.getCode());
                log.info("{}의 포만감이 감소하여 배고픔 상태에 들어갑니다.", mongId);
                webSocketService.sendStatus(mong, WebSocketCode.HUNGRY);
            }
        }

        if(satiety == 0){
            log.info("{}의 죽음의 카운트가 시작됩니다.", mongId);
            webSocketService.sendStatus(mong, WebSocketCode.DEATH_READY);
            return false;
        }
        log.info("{}의 포만감이 감소하였습니다.", mongId);
        webSocketService.sendStatus(mong, WebSocketCode.SUCCESS);
        return true;
    }
}
