package com.paymong.management.feed.service;

import com.paymong.management.feed.vo.FeedFoodReqVo;
import com.paymong.management.feed.vo.FeedSnackReqVo;
import com.paymong.management.global.exception.NotFoundActionException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.status.entitiy.Status;
import com.paymong.management.status.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final MongRepository mongRepository;
    private final StatusRepository statusRepository;

    @Transactional
    public void feedFood(FeedFoodReqVo feedFoodReqVo) throws Exception{
        // auth에서 mongId 받아오기

        // mongId로 해당 mong 찾기
        Mong mong = mongRepository.findByMongId(1L)
                .orElseThrow(() -> new NotFoundMongException());

        // foodCode에 따라 해당 액션 찾기
        String foodCode = feedFoodReqVo.getFoodCode().substring(2);
        int code = Integer.parseInt(foodCode);

        Status status = null;
        if(code == 0){
            status = statusRepository.findByCode("AT000")
                    .orElseThrow(()->new NotFoundActionException());
        }else if(1<=code && 3>=code){
            status = statusRepository.findByCode("AT010")
                    .orElseThrow(()->new NotFoundActionException());
        }else if(4<=code && 6>=code){
            status = statusRepository.findByCode("AT020")
                    .orElseThrow(()->new NotFoundActionException());
        }else{
            status = statusRepository.findByCode("AT030")
                    .orElseThrow(()->new NotFoundActionException());
        }

        mong.setWeight(mong.getWeight() + status.getWeight());

    }

    @Transactional
    public void feedSnack(FeedSnackReqVo feedSnackReqVo) throws Exception{
        // auth에서 mongId 받아오기

        // mongId로 해당 mong 찾기
        Mong mong = mongRepository.findByMongId(1L)
                .orElseThrow(() -> new NotFoundMongException());

        // foodCode에 따라 해당 액션 찾기
        String foodCode = feedSnackReqVo.getSnackCode().substring(2);
        int code = Integer.parseInt(foodCode);

        Status status = null;
        if(code>= 0 && code<= 3){
            status = statusRepository.findByCode("AT001")
                    .orElseThrow(()->new NotFoundActionException());
        }else{
            status = statusRepository.findByCode("AT011")
                    .orElseThrow(()->new NotFoundActionException());
        }

        mong.setWeight(mong.getWeight() + status.getWeight());
        mong.setHealth(mong.getHealth() + status.getHealth());
        mong.setStrength(mong.getStrength() + status.getStrength());
    }
}
