package com.paymong.management.training.service;

import com.paymong.management.global.exception.NotFoundActionException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.status.entitiy.Status;
import com.paymong.management.status.repository.StatusRepository;
import com.paymong.management.training.vo.WalkingReqVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final MongRepository mongRepository;
    private final StatusRepository statusRepository;

    @Transactional
    public void training() throws Exception{
        // mong id 받아오기

        // mong id로 mong 찾기
        Mong mong = mongRepository.findByMongId(1L)
                .orElseThrow(() -> new NotFoundMongException());

        // training action 찾기
        Status status = statusRepository.findByCode("AT005")
                .orElseThrow(() -> new NotFoundActionException());

        mong.setStrength(mong.getStrength() + status.getStrength());
        mong.setHealth(mong.getHealth() + status.getHealth() < 0 ? 0 : mong.getHealth() + status.getHealth());
        mong.setSatiety(mong.getSatiety() + status.getSatiety() < 0 ? 0 : mong.getSatiety() + status.getSatiety());
        mong.setSleep(mong.getSleep() + status.getSleep() < 0 ? 0 : mong.getSleep() + status.getSleep());
    }

    @Transactional
    public void walking(WalkingReqVo walkingReqVo) throws Exception{
        // mong id 받아오기

        // mong id로 mong 찾기
        Mong mong = mongRepository.findByMongId(1L)
                .orElseThrow(() -> new NotFoundMongException());

        // training action 찾기
        Status status = statusRepository.findByCode("AT004")
                .orElseThrow(() -> new NotFoundActionException());

        Integer cnt = walkingReqVo.getWalkingCount()/500;

        mong.setStrength(mong.getStrength() + (status.getStrength()*cnt));
        mong.setHealth(mong.getHealth() + status.getHealth() < 0 ? 0 : mong.getHealth() + status.getHealth());
        mong.setSatiety(mong.getSatiety() + status.getSatiety() < 0 ? 0 : mong.getSatiety() + status.getSatiety());
        mong.setSleep(mong.getSleep() + status.getSleep() < 0 ? 0 : mong.getSleep() + status.getSleep());
    }

}
