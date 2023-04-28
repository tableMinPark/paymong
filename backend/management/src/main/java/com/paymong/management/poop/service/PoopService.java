package com.paymong.management.poop.service;

import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.poop.vo.PoopMongReqVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PoopService {
    private final MongRepository mongRepository;
    @Transactional
    public void removePoop(PoopMongReqVo poopMongReqVo) throws Exception{
        Mong mong = mongRepository.findByMongId(poopMongReqVo.getMongId())
                .orElseThrow(() -> new NotFoundMongException());
        mong.setPoopCount(0);
    }

}
