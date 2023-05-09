package com.paymong.management.status.service;

import com.paymong.management.global.code.MongConditionCode;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.scheduler.service.SchedulerService;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.status.dto.FindStatusResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatusService {

    private final MongRepository mongRepository;
    @Transactional
    public void modifyMongStatus(Long mongId, FindStatusResDto statusResDto) throws NotFoundMongException {
        Mong mong = mongRepository.findByMongId(mongId)
                .orElseThrow(() -> new NotFoundMongException());

        Integer level = Integer.parseInt(mong.getCode().substring(2,3));

        // strength 레벨 별 20 30 40
        Integer strength = checkLevel(level, mong.getStrength(), statusResDto.getStrength());
        // health 레벨 별 20 30 40
        Integer health = checkLevel(level, mong.getHealth(), statusResDto.getHealth());
        if(health == 0) {
            // 스케줄러}
        }
        // satiety 레벨 별 20 30 40
        Integer satiety = checkLevel(level, mong.getSatiety(), statusResDto.getSatiety());
        if(satiety == 0) {
            // 스케줄러
        }
        // sleep 최대 20
        Integer sleep = checkLevel(level == 0 ? 0 : 1, mong.getSleep(), statusResDto.getSleep());
        // weight 레벨별 최소 5 15 25 35 최대 99
        Integer weight = checkWeight(level, mong.getWeight(), statusResDto.getWeight());

        mong.setStrength(strength);
        mong.setHealth(health);
        mong.setSatiety(satiety);
        mong.setSleep(sleep);
        mong.setWeight(weight);
    }

    public MongConditionCode checkCondition(Mong mong) {
        if(mong.getPoopCount() == 4 || mong.getHealth() == 0){
            return MongConditionCode.SICK;
        }else if(mong.getSleep() < 2){
            return MongConditionCode.SOMNOLENCE;
        }else if(mong.getSatiety() < 5){
            return MongConditionCode.HUNGRY;
        }else{
            return MongConditionCode.NORMAL;
        }
    }

    private Integer checkLevel(Integer level, Integer value, Integer add){
        int rst = value;
        if(level == 0){
            // 알인 상태에서는 그대로 반환
        }else if(level == 1){
            // 최소 0 최대 20
            rst = rst + add;
            rst = rst > 20 ? 20 : rst;
            rst = rst < 0 ? 0 : rst;
        }else if(level == 2) {
            // 최소 0 최대 30
            rst = rst + add;
            rst = rst > 30 ? 30 : rst;
            rst = rst < 0 ? 0 : rst;
        }else if(level == 3) {
            // 최소 0 최대 40
            rst = rst + add;
            rst = rst > 40 ? 40 : rst;
            rst = rst < 0 ? 0 : rst;
        }
        return rst;
    }

    private Integer checkWeight(Integer level, Integer value, Integer add){
        int rst = value;
        if(level == 0){
            // 최소 5 최대 99
            rst = rst + add;
            rst = rst > 99 ? 99 : rst;
            rst = rst < 5 ? 5 : rst;
        }else if(level == 1){
            // 최소 15 최대 99
            rst = rst + add;
            rst = rst > 99 ? 99 : rst;
            rst = rst < 15 ? 15 : rst;
        }else if(level == 2) {
            // 최소 25 최대 99
            rst = rst + add;
            rst = rst > 99 ? 99 : rst;
            rst = rst < 25 ? 25 : rst;
        }else if(level == 3) {
            // 최소 35 최대 99
            rst = rst + add;
            rst = rst > 99 ? 99 : rst;
            rst = rst < 35 ? 35 : rst;
        }

        return rst;
    }
}
