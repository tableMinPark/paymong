package com.paymong.information.information.service;


import com.paymong.information.global.exception.NotFoundMongException;
import com.paymong.information.information.dto.FindMongBattleDto;
import com.paymong.information.information.dto.FindMongDto;
import com.paymong.information.information.dto.FindMongInfoDto;
import com.paymong.information.information.dto.FindMongStatusDto;
import com.paymong.information.information.entity.Mong;
import com.paymong.information.information.repository.MongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MongService {

    private final MongRepository mongRepository;

    @Transactional
    public FindMongDto findMong(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findById(mongId).orElseThrow(() -> new NotFoundMongException());
        FindMongDto findMongDto = new FindMongDto(mong);

        return findMongDto;
    }

    @Transactional
    public FindMongInfoDto findMongInfo(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findById(mongId).orElseThrow(() -> new NotFoundMongException());
        FindMongInfoDto findMongInfoDto = new FindMongInfoDto(mong);

        return findMongInfoDto;
    }

    @Transactional
    public FindMongStatusDto findMongStatus(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findById(mongId).orElseThrow(() -> new NotFoundMongException());
        FindMongStatusDto findMongStatusDto = new FindMongStatusDto(mong);

        int level = Integer.parseInt(mong.getCode().substring(2,3));

        Double health = 0.0;
        Double satiety = 0.0;
        Double strength = 0.0;
        Double sleep = 0.0;

        if(level == 1){
            health = mong.getHealth()/20.0;
            satiety = mong.getSatiety()/20.0;
            strength = mong.getStrength()/20.0;
            sleep = mong.getSleep()/20.0;
        }else if(level == 2){
            health = mong.getHealth()/30.0;
            satiety = mong.getSatiety()/30.0;
            strength = mong.getStrength()/30.0;
            sleep = mong.getSleep()/30.0;

        }else if(level == 3){
            health = mong.getHealth()/40.0;
            satiety = mong.getSatiety()/40.0;
            strength = mong.getStrength()/40.0;
            sleep = mong.getSleep()/40.0;
        }

        findMongStatusDto.setHealth(Math.round(health * 100.0)/100.0);
        findMongStatusDto.setSatiety(Math.round(satiety * 100.0)/100.0);
        findMongStatusDto.setStrength(Math.round(strength * 100.0)/100.0);
        findMongStatusDto.setSleep(Math.round(sleep * 100.0)/100.0);

        return findMongStatusDto;
    }

    // 배틀부분
    @Transactional
    public FindMongBattleDto findMongBattle(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findById(mongId).orElseThrow(() -> new NotFoundMongException());
        FindMongBattleDto findMongBattleDto = new FindMongBattleDto(mong);

        return findMongBattleDto;
    }

}
