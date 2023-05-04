package com.paymong.management.mong.service;

import com.paymong.management.global.client.CommonServiceClient;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.scheduler.service.SchedulerService;
import com.paymong.management.mong.dto.FindRandomEggDto;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.mong.vo.AddMongReqVo;
import com.paymong.management.mong.vo.AddMongResVo;
import com.paymong.management.mong.vo.FindMongReqVo;
import com.paymong.management.mong.vo.FindMongResVo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MongService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongService.class);
    private final MongRepository mongRepository;
    private final CommonServiceClient commonServiceClient;

    @Transactional
    public AddMongResVo addMong(AddMongReqVo addMongReqVo) throws Exception{

        FindRandomEggDto findRandomEggDto = commonServiceClient.findRandomEgg();

        Mong mong = Mong.builder()
                .name(addMongReqVo.getName())
                .memberId(addMongReqVo.getMemberId())
                .code(findRandomEggDto.getCode())
                .sleepStart(addMongReqVo.getSleepStart())
                .sleepEnd(addMongReqVo.getSleepEnd())
                .build();

        Mong newMong = mongRepository.save(mong);

        AddMongResVo addMongResVo = new AddMongResVo(newMong);
        // 무슨 이유인진 몰라도 null로 처리됨..
        addMongResVo.setWeight(5);
        addMongResVo.setBorn(LocalDate.now());

        return addMongResVo;
    }

    @Transactional
    public FindMongResVo findMongByMember(FindMongReqVo findMongReqVo) throws Exception{
        Mong mong = mongRepository.findByMemberIdAndActive(findMongReqVo.getMemberId(), true)
                .orElseThrow(()-> new NotFoundMongException());

        FindMongResVo findMongResVo = new FindMongResVo();
        findMongResVo.setMongId(mong.getMongId());
        findMongResVo.setMongCode(mong.getCode());
        return findMongResVo;
    }

    @Transactional
    public void reduceHealth(Long mongId) throws NotFoundMongException {
        Mong mong = mongRepository.findByMongId(mongId)
                .orElseThrow(() -> new NotFoundMongException());
        if(!mong.getActive()) throw new NotFoundMongException();
        Integer poop = mong.getPoopCount();
        Integer health = mong.getHealth();
        Integer penalty = mong.getPenalty();
        if(health == 0){
            // 체력이 0인데 관리를 안하면 괘씸죄
            mong.setPenalty(penalty + 1);
        }else{
            if(poop == 0){
                health = health - 1 < 0 ? 0 : health - 1;
                mong.setHealth(health);
            }else{
                health = health - (poop*poop) < 0 ? 0 : health - (poop*poop);
                mong.setHealth(health);
            }
            if(health == 0){
                LOGGER.info("{}의 죽음의 카운트가 시작됩니다.", mongId);
            }
        }

    }
}
