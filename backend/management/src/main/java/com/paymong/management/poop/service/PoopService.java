package com.paymong.management.poop.service;

import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.poop.vo.PoopMongReqVo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PoopService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoopService.class);
    private final MongRepository mongRepository;
    int cnt = 0;
    @Transactional
    public void removePoop(PoopMongReqVo poopMongReqVo) throws Exception{
        Mong mong = mongRepository.findByMongId(poopMongReqVo.getMongId())
                .orElseThrow(() -> new NotFoundMongException());
        mong.setPoopCount(0);
    }

    @Transactional
    public void addPoop(Long mongId) throws NotFoundMongException {

        Mong mong = mongRepository.findByMongId(mongId)
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
