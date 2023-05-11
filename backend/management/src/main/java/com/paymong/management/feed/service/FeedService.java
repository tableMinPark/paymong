package com.paymong.management.feed.service;

import com.paymong.management.feed.vo.FeedFoodReqVo;
import com.paymong.management.feed.vo.FeedSnackReqVo;
import com.paymong.management.global.client.ClientService;
import com.paymong.management.global.dto.AddPointDto;
import com.paymong.management.global.dto.CommonCodeDto;
import com.paymong.management.global.dto.FindCommonCodeDto;
import com.paymong.management.global.exception.NotFoundActionException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.history.entity.ActiveHistory;
import com.paymong.management.history.repository.ActiveHistoryRepository;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.status.dto.FindStatusReqDto;
import com.paymong.management.status.dto.FindStatusResDto;
import com.paymong.management.status.dto.MongStatusDto;
import com.paymong.management.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FeedService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedService.class);
    private final MongRepository mongRepository;
    private final ClientService clientService;
    private final StatusService statusService;
    private final ActiveHistoryRepository activeHistoryRepository;

    @Transactional
    public MongStatusDto feedFood(FeedFoodReqVo feedFoodReqVo) throws Exception{
        // foodCode에 따라 해당 액션 찾기
        String foodCode = feedFoodReqVo.getFoodCode().substring(0,2);
        if(!foodCode.equals("FD")) throw new NotFoundActionException();

        foodCode = feedFoodReqVo.getFoodCode().substring(3,4);
        int code = Integer.parseInt(foodCode);

        FindStatusReqDto findStatusReqDto = new FindStatusReqDto();
        if(code == 0){
            findStatusReqDto.setCode("AT000");
        }else if(code == 1){
            findStatusReqDto.setCode("AT010");
        }else if(code == 2){
            findStatusReqDto.setCode("AT020");
        }else if(code == 3){
            findStatusReqDto.setCode("AT030");
        }else {
            throw new NotFoundActionException();
        }

        FindStatusResDto status = clientService.findStatus(findStatusReqDto);

        CommonCodeDto food = clientService.findCommonCode(new FindCommonCodeDto(feedFoodReqVo.getFoodCode()));

        LOGGER.info("활동 코드 : {} , 음식 코드 : {}, 음식 이름 : {}, 음식 가격 : {}",findStatusReqDto.getCode(), food.getCode(), food.getName(), status.getPoint());

        clientService.addPoint(String.valueOf(feedFoodReqVo.getMemberId()), new AddPointDto(status.getPoint(), food.getName() + " 구매", food.getCode()));

        MongStatusDto mongStatusDto = statusService.modifyMongStatus(feedFoodReqVo.getMongId(), status);

        ActiveHistory activeHistory = ActiveHistory.builder()
                .activeCode(food.getCode())
                .activeTime(LocalDateTime.now())
                .mongId(feedFoodReqVo.getMongId())
                .build();

        activeHistoryRepository.save(activeHistory);

        return mongStatusDto;
    }

    @Transactional
    public MongStatusDto feedSnack(FeedSnackReqVo feedSnackReqVo) throws Exception{
        // mongId로 해당 mong 찾기
        Mong mong = mongRepository.findByMongId(feedSnackReqVo.getMongId())
                .orElseThrow(() -> new NotFoundMongException());

        String foodCode = feedSnackReqVo.getSnackCode().substring(0,2);
        if(!foodCode.equals("SN")) throw new NotFoundActionException();
        // foodCode에 따라 해당 액션 찾기
        foodCode = feedSnackReqVo.getSnackCode().substring(3,4);
        int code = Integer.parseInt(foodCode);

        FindStatusReqDto findStatusReqDto = new FindStatusReqDto();
        if(code == 0){
            findStatusReqDto.setCode("AT001");
        }else if(code == 1){
            // code == 1
            findStatusReqDto.setCode("AT011");
        }else{
            throw new NotFoundActionException();
        }

        FindStatusResDto status = clientService.findStatus(findStatusReqDto);

        CommonCodeDto food = clientService.findCommonCode(new FindCommonCodeDto(feedSnackReqVo.getSnackCode()));

        LOGGER.info("활동 코드 : {} , 간식 코드 : {}, 간식 이름 : {}, 간식 가격 : {}",findStatusReqDto.getCode(), food.getCode(), food.getName(), status.getPoint());

        clientService.addPoint(
                String.valueOf(feedSnackReqVo.getMemberId()),
                new AddPointDto(status.getPoint(),
         food.getName() + " 구매",
                food.getCode()));

        MongStatusDto mongStatusDto = statusService.modifyMongStatus(feedSnackReqVo.getMongId(), status);

        ActiveHistory activeHistory = ActiveHistory.builder()
                .activeCode(food.getCode())
                .activeTime(LocalDateTime.now())
                .mongId(feedSnackReqVo.getMongId())
                .build();

        activeHistoryRepository.save(activeHistory);

        return mongStatusDto;
    }
}
