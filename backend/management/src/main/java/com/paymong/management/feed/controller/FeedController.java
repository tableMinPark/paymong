package com.paymong.management.feed.controller;

import com.paymong.management.feed.dto.FeedFoodReqDto;
import com.paymong.management.feed.dto.FeedSnackReqDto;
import com.paymong.management.feed.service.FeedService;
import com.paymong.management.feed.vo.FeedFoodReqVo;
import com.paymong.management.feed.vo.FeedSnackReqVo;
import com.paymong.management.global.code.ManagementStateCode;
import com.paymong.management.global.exception.NotFoundActionException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management/feed")
public class FeedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedController.class);
    private final FeedService feedService;
    /* 음식 먹이기 */
    @PostMapping("/food")
    public ResponseEntity<Object> feedFood(FeedFoodReqDto feedFoodReqDto) throws Exception{
        FeedFoodReqVo feedFoodReqVo = new FeedFoodReqVo(feedFoodReqDto);
        try {
            if(feedFoodReqVo.getFoodCode() == null){
                throw new NullPointerException();
            }
            feedService.feedFood(feedFoodReqVo);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
        }catch (NullPointerException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NULL_POINT.getCode(), ManagementStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NULL_POINT));
        }catch (NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_FOUND.getCode(), ManagementStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_FOUND));
        }catch (NotFoundActionException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_ACTION.getCode(), ManagementStateCode.NOT_ACTION.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_ACTION));
        }
    }

    /* 간식 먹이기 */
    @PostMapping("/snack")
    public ResponseEntity<Object> feedSnack(FeedSnackReqDto feedSnackReqDto) throws Exception{
        FeedSnackReqVo feedSnackReqVo = new FeedSnackReqVo(feedSnackReqDto);
        try {
            if(feedSnackReqVo.getSnackCode() == null){
                throw new NullPointerException();
            }
            feedService.feedSnack(feedSnackReqVo);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(ManagementStateCode.SUCCESS));
        }catch (NullPointerException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NULL_POINT.getCode(), ManagementStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NULL_POINT));
        }catch (NotFoundMongException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_FOUND.getCode(), ManagementStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_FOUND));
        }catch (NotFoundActionException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.NOT_ACTION.getCode(), ManagementStateCode.NOT_ACTION.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.NOT_ACTION));
        }
    }
}
