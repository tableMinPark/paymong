package com.paymong.management.global.scheduler.task;

import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PoopTask {

    private final MongRepository mongRepository;
    @Transactional
    public void addPoop(Long mongId) throws NotFoundMongException {

        Mong mong = mongRepository.findByMongIdAndActive(mongId, true)
                .orElseThrow(() -> new NotFoundMongException());
        if(!mong.getActive()) throw new NotFoundMongException();
        Integer poop = mong.getPoopCount();
        Integer penalty = mong.getPenalty();
        if(poop == 4){
            // 패널티 적립
            mong.setPenalty(penalty + 1);
        }else{
            // 갯수 적립
            mong.setPoopCount(poop + 1);
        }
    }
}
