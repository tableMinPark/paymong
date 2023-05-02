package com.paymong.management.training.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.management.global.client.CommonServiceClient;
import com.paymong.management.global.exception.NotFoundActionException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.status.dto.FindStatusReqDto;
import com.paymong.management.status.dto.FindStatusResDto;
import com.paymong.management.training.vo.WalkingReqVo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TrainingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingService.class);
    private final MongRepository mongRepository;
    private final CommonServiceClient commonServiceClient;

    @Transactional
    public void training(Long mongId) throws Exception{
        // mong id 받아오기

        // mong id로 mong 찾기
        Mong mong = mongRepository.findByMongId(mongId)
                .orElseThrow(() -> new NotFoundMongException());

        // training action 찾기
        FindStatusReqDto findStatusReqDto = new FindStatusReqDto("AT005");
        ObjectMapper om = new ObjectMapper();
        ResponseEntity<Object> response = commonServiceClient.findStatus(findStatusReqDto);
        if(response.getStatusCode()== HttpStatus.BAD_REQUEST) throw new NotFoundActionException();
        FindStatusResDto status = om.convertValue(response.getBody(), FindStatusResDto.class);

        mong.setStrength(mong.getStrength() + status.getStrength());
        mong.setHealth(mong.getHealth() + status.getHealth() < 0 ? 0 : mong.getHealth() + status.getHealth());
        mong.setSatiety(mong.getSatiety() + status.getSatiety() < 0 ? 0 : mong.getSatiety() + status.getSatiety());
        mong.setSleep(mong.getSleep() + status.getSleep() < 0 ? 0 : mong.getSleep() + status.getSleep());
    }

    @Transactional
    public void walking(WalkingReqVo walkingReqVo) throws Exception{
        // 500걸음 이상인지 체크
        if(walkingReqVo.getWalkingCount() < 500){
            LOGGER.info("500걸음 이하 입니다.");
            return;
        }

        // mong id로 mong 찾기
        Mong mong = mongRepository.findByMongId(walkingReqVo.getMongId())
                .orElseThrow(() -> new NotFoundMongException());

        // training action 찾기
        FindStatusReqDto findStatusReqDto = new FindStatusReqDto("AT004");
        ObjectMapper om = new ObjectMapper();
        ResponseEntity<Object> response = commonServiceClient.findStatus(findStatusReqDto);
        if(response.getStatusCode()== HttpStatus.BAD_REQUEST) throw new NotFoundActionException();
        FindStatusResDto status = om.convertValue(response.getBody(), FindStatusResDto.class);

        Integer cnt = walkingReqVo.getWalkingCount()/500;
        Integer point = walkingReqVo.getWalkingCount()/100;
        // mongId 담고
        // point 담고
        // 종목 담고
        mong.setStrength(mong.getStrength() + (status.getStrength()*cnt));
        mong.setHealth(mong.getHealth() + status.getHealth() < 0 ? 0 : mong.getHealth() + status.getHealth());
        mong.setSatiety(mong.getSatiety() + status.getSatiety() < 0 ? 0 : mong.getSatiety() + status.getSatiety());
        mong.setSleep(mong.getSleep() + status.getSleep() < 0 ? 0 : mong.getSleep() + status.getSleep());
    }

}
