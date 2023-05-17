package com.paymong.information.information.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.information.global.client.MemberServiceClient;
import com.paymong.information.global.exception.NotFoundMongException;
import com.paymong.information.information.dto.FindLastBuyResDto;
import com.paymong.information.information.dto.FindMongBattleDto;
import com.paymong.information.information.dto.FindMongDto;
import com.paymong.information.information.dto.FindMongInfoDto;
import com.paymong.information.information.dto.FindMongMasterResDto;
import com.paymong.information.information.dto.FindMongStatusDto;
import com.paymong.information.information.dto.FindMymapResDto;
import com.paymong.information.information.entity.ActiveHistory;
import com.paymong.information.information.entity.Mong;
import com.paymong.information.information.repository.ActiveHistroyRepository;
import com.paymong.information.information.repository.MongRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MongService {

    private final MongRepository mongRepository;

    private final ActiveHistroyRepository activeHistroyRepository;

    private final MemberServiceClient memberServiceClient;

    @Transactional
    public FindMongDto findMong(Long mongId, String memberIdStr) throws NotFoundMongException {
        Mong mong = mongRepository.findById(mongId).orElseThrow(() -> new NotFoundMongException());
        String mapCode;
        try {
            ObjectMapper om = new ObjectMapper();
            FindMymapResDto findMymapResDto = om.convertValue(memberServiceClient.findMymap(memberIdStr).getBody(),
                FindMymapResDto.class);
            mapCode = findMymapResDto.getMapCode();
            log.info(mapCode);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new NullPointerException();
        }
        FindMongDto findMongDto = new FindMongDto(mong, mapCode);
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

        int level = Integer.parseInt(mong.getCode().substring(2, 3));
        int tier = Integer.parseInt(mong.getCode().substring(3,4));
        Double health = 0.0;
        Double satiety = 0.0;
        Double strength = 0.0;
        Double sleep = 0.0;

        if (level == 1) {
            health = mong.getHealth() / 20.0;
            satiety = mong.getSatiety() / 20.0;
            strength = mong.getStrength() / 20.0;
            sleep = mong.getSleep() / 20.0;
        } else if (level == 2) {
            if(tier == 1){
                health = mong.getHealth() / 30.0;
                satiety = mong.getSatiety() / 30.0;
                strength = mong.getStrength() / 30.0;
                sleep = mong.getSleep() / 30.0;
            }else if(tier == 2){
                health = mong.getHealth() / 35.0;
                satiety = mong.getSatiety() / 35.0;
                strength = mong.getStrength() / 35.0;
                sleep = mong.getSleep() / 35.0;
            }else if(tier == 3){
                health = mong.getHealth() / 40.0;
                satiety = mong.getSatiety() / 40.0;
                strength = mong.getStrength() / 40.0;
                sleep = mong.getSleep() / 40.0;
            }else{
                health = mong.getHealth() / 25.0;
                satiety = mong.getSatiety() / 25.0;
                strength = mong.getStrength() / 25.0;
                sleep = mong.getSleep() / 25.0;
            }

        } else if (level == 3) {
            if(tier == 1){
                health = mong.getHealth() / 40.0;
                satiety = mong.getSatiety() / 40.0;
                strength = mong.getStrength() / 40.0;
                sleep = mong.getSleep() / 40.0;
            }else if(tier == 2){
                health = mong.getHealth() / 45.0;
                satiety = mong.getSatiety() / 45.0;
                strength = mong.getStrength() / 45.0;
                sleep = mong.getSleep() / 45.0;
            }else if(tier == 3){
                health = mong.getHealth() / 50.0;
                satiety = mong.getSatiety() / 50.0;
                strength = mong.getStrength() / 50.0;
                sleep = mong.getSleep() / 50.0;
            }else{
                health = mong.getHealth() / 35.0;
                satiety = mong.getSatiety() / 35.0;
                strength = mong.getStrength() / 35.0;
                sleep = mong.getSleep() / 35.0;
            }
        }

        findMongStatusDto.setHealth(Math.round(health * 100.0) / 100.0);
        findMongStatusDto.setSatiety(Math.round(satiety * 100.0) / 100.0);
        findMongStatusDto.setStrength(Math.round(strength * 100.0) / 100.0);
        findMongStatusDto.setSleep(Math.round(sleep * 100.0) / 100.0);

        return findMongStatusDto;
    }

    // 배틀부분
    @Transactional
    public FindMongBattleDto findMongBattle(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findById(mongId).orElseThrow(() -> new NotFoundMongException());
        FindMongBattleDto findMongBattleDto = new FindMongBattleDto(mong);

        return findMongBattleDto;
    }

    @Transactional
    public FindLastBuyResDto findLastBuy(Long mongId, String foodCode)
        throws NotFoundMongException {
        Mong mong = mongRepository.findById(mongId).orElseThrow(() -> new NotFoundMongException());
        Optional<ActiveHistory> activeHistory = activeHistroyRepository.findTopByMongIdAndActiveCodeOrderByActiveTimeDesc(
            mongId, foodCode);

        if (activeHistory.isPresent()) {
            log.info("isPresent - {}", activeHistory.isPresent());
            FindLastBuyResDto findLasyBuyResDto = new FindLastBuyResDto(activeHistory.get().getActiveTime());
            return findLasyBuyResDto;
        } else {
            log.info("isPresent - {}", activeHistory.isPresent());
            return new FindLastBuyResDto(null);
        }
    }

    @Transactional
    public FindMongMasterResDto findMongMaster(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findById(mongId).orElseThrow(() -> new NotFoundMongException());
        return new FindMongMasterResDto(mong.getMemberId());
    }

}
