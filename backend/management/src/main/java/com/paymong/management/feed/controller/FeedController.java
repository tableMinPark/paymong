package com.paymong.management.feed.controller;

import com.paymong.management.feed.dto.FeedFoodReqDto;
import com.paymong.management.feed.dto.FeedSnackReqDto;
import com.paymong.management.feed.service.FeedService;
import com.paymong.management.feed.vo.FeedFoodReqVo;
import com.paymong.management.feed.vo.FeedSnackReqVo;
import com.paymong.management.global.code.ManagementStateCode;
import com.paymong.management.global.exception.GatewayException;
import com.paymong.management.global.exception.NotFoundActionException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.exception.UnknownException;
import com.paymong.management.global.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management/feed")
public class FeedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedController.class);
    private final FeedService feedService;
    @Value("${header.mong}")
    String headerMong;

    @Value("${header.member}")
    String headerMember;
    /* 음식 먹이기 */
    @PutMapping("/food")
    public ResponseEntity<Object> feedFood(@RequestBody FeedFoodReqDto feedFoodReqDto, HttpServletRequest httpServletRequest) throws Exception{
        FeedFoodReqVo feedFoodReqVo = new FeedFoodReqVo(feedFoodReqDto);
        String mongIdStr = httpServletRequest.getHeader(headerMong);
        String memberIdStr = httpServletRequest.getHeader(headerMember);
        try {
            if(feedFoodReqVo.getFoodCode() == null){
                throw new NullPointerException();
            }
            if(mongIdStr == null || mongIdStr.equals("")){
                throw new NullPointerException();
            }
            if(memberIdStr == null || memberIdStr.equals("")){
                throw new NullPointerException();
            }
            Long mongId = Long.parseLong(mongIdStr);
            Long memberId = Long.parseLong(memberIdStr);

            feedFoodReqVo.setMongId(mongId);
            feedFoodReqVo.setMemberId(memberId);

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
        }catch (UnknownException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.UNKNOWN.getCode(), ManagementStateCode.UNKNOWN.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.UNKNOWN));
        }catch (GatewayException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.GATEWAY_ERROR.getCode(), ManagementStateCode.GATEWAY_ERROR.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.GATEWAY_ERROR));
        }
    }

    /* 간식 먹이기 */
    @PutMapping("/snack")
    public ResponseEntity<Object> feedSnack(@RequestBody FeedSnackReqDto feedSnackReqDto, HttpServletRequest httpServletRequest) throws Exception{
        FeedSnackReqVo feedSnackReqVo = new FeedSnackReqVo(feedSnackReqDto);
        String mongIdStr = httpServletRequest.getHeader(headerMong);
        String memberIdStr = httpServletRequest.getHeader(headerMember);
        try {
            if(feedSnackReqVo.getSnackCode() == null){
                throw new NullPointerException();
            }
            if(mongIdStr == null || mongIdStr.equals("")){
                throw new NullPointerException();
            }
            if(memberIdStr == null || memberIdStr.equals("")){
                throw new NullPointerException();
            }
            Long mongId = Long.parseLong(mongIdStr);
            Long memberId = Long.parseLong(memberIdStr);

            feedSnackReqVo.setMongId(mongId);
            feedSnackReqVo.setMemberId(memberId);

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
        }catch (UnknownException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.UNKNOWN.getCode(), ManagementStateCode.UNKNOWN.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.UNKNOWN));
        }catch (GatewayException e){
            LOGGER.info("code : {}, message : {}", ManagementStateCode.GATEWAY_ERROR.getCode(), ManagementStateCode.GATEWAY_ERROR.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ManagementStateCode.GATEWAY_ERROR));
        }
    }
}
