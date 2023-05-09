package com.paymong.management.training.service;

import com.paymong.management.global.client.ClientService;
import com.paymong.management.global.dto.AddPointDto;
import com.paymong.management.history.entity.ActiveHistory;
import com.paymong.management.history.repository.ActiveHistoryRepository;
import com.paymong.management.status.dto.FindStatusReqDto;
import com.paymong.management.status.dto.FindStatusResDto;
import com.paymong.management.status.service.StatusService;
import com.paymong.management.training.vo.TrainingReqVo;
import com.paymong.management.training.vo.WalkingReqVo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TrainingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingService.class);

    private final ClientService clientService;
    private final StatusService statusService;
    private final ActiveHistoryRepository activeHistoryRepository;
    @Transactional
    public void training(TrainingReqVo trainingReqVo) throws Exception{

        // training action 찾기
        FindStatusReqDto findStatusReqDto = new FindStatusReqDto("AT005");

        FindStatusResDto status = clientService.findStatus(findStatusReqDto);

        LOGGER.info("member : {}, point : {}", trainingReqVo.getMemberId(), status.getPoint());

        // auth 서비스로 전송
        clientService.addPoint(String.valueOf(trainingReqVo.getMemberId()), new AddPointDto(status.getPoint(), "훈련", status.getCode()));

        ActiveHistory activeHistory = ActiveHistory.builder()
                .activeCode(status.getCode())
                .activeTime(LocalDateTime.now())
                .mongId(trainingReqVo.getMongId())
                .build();

        activeHistoryRepository.save(activeHistory);

        if(trainingReqVo.getWalkingCount() >= 50){
            // 수치값 변경
            statusService.modifyMongStatus(trainingReqVo.getMongId(), status);
        }

    }

    @Transactional
    public void walking(WalkingReqVo walkingReqVo) throws Exception{
        // 500걸음 이상인지 체크
        if(walkingReqVo.getWalkingCount() < 500){
            LOGGER.info("500걸음 이하 입니다.");
            return;
        }

        // training action 찾기
        FindStatusReqDto findStatusReqDto = new FindStatusReqDto("AT004");

        FindStatusResDto status = clientService.findStatus(findStatusReqDto);

        Integer cnt = walkingReqVo.getWalkingCount()/500;

        Integer point = walkingReqVo.getWalkingCount()/100;

        // auth 서비스로 전송
        clientService.addPoint(String.valueOf(walkingReqVo.getMemberId()), new AddPointDto(point * status.getPoint(), "산책", status.getCode()));

        status.setStrength(status.getStrength() * (status.getStrength()*cnt));

        ActiveHistory activeHistory = ActiveHistory.builder()
                .activeCode(status.getCode())
                .activeTime(LocalDateTime.now())
                .mongId(walkingReqVo.getMongId())
                .build();

        activeHistoryRepository.save(activeHistory);

        // 수치값 변경
        statusService.modifyMongStatus(walkingReqVo.getMongId(), status);
    }

}
