package com.paymong.information.information.controller;

import com.paymong.information.global.code.InformationStateCode;
import com.paymong.information.global.exception.NotFoundMongException;
import com.paymong.information.global.response.ErrorResponse;
import com.paymong.information.information.dto.FindLastBuyReqDto;
import com.paymong.information.information.dto.FindLastBuyResDto;
import com.paymong.information.information.dto.FindMongBattleDto;
import com.paymong.information.information.dto.FindMongDto;
import com.paymong.information.information.dto.FindMongInfoDto;
import com.paymong.information.information.dto.FindMongMasterReqDto;
import com.paymong.information.information.dto.FindMongMasterResDto;
import com.paymong.information.information.dto.FindMongStatusDto;
import com.paymong.information.information.dto.MongBattleDto;
import com.paymong.information.information.service.MongService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/information")
public class InformationController {

    private final MongService mongService;

    @Value("${header.mong}")
    String headerMong;

    @Value("${header.member}")
    String headerMember;

    @GetMapping("/mong")
    public ResponseEntity<Object> findMong(HttpServletRequest httpServletRequest) {
        String mongIdStr = httpServletRequest.getHeader(headerMong);
        String memberIdStr = httpServletRequest.getHeader(headerMember);
        log.info("{}", memberIdStr);

        try {
            // 한번도 몽 생성안 한 경우
            if ("".equals(mongIdStr)) {
                return ResponseEntity.ok()
                    .body(FindMongDto.builder()
                        .mongId(0L)
                        .name("")
                        .mongCode("CH444")
                        .stateCode("CD000")
                        .poopCount(0)
                        .mapCode("MP000")
                        .build());
            }

            Long mongId = Long.parseLong(mongIdStr);
            FindMongDto findMongDto = mongService.findMong(mongId, memberIdStr);
            return ResponseEntity.ok().body(findMongDto);
        } catch (NullPointerException e) {
            log.info("code : {}, message : {}", InformationStateCode.NULL_POINT.getCode(),
                InformationStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(InformationStateCode.NULL_POINT));
        } catch (NotFoundMongException e) {
            log.info("code : {}, message : {}", InformationStateCode.NOT_FOUND.getCode(),
                InformationStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(InformationStateCode.NOT_FOUND));
        }
    }

    @GetMapping("/mong/info")
    public ResponseEntity<Object> findMongInfo(HttpServletRequest httpServletRequest) {
        String mongIdStr = httpServletRequest.getHeader(headerMong);

        try {
            if (mongIdStr == null || mongIdStr.equals("")) {
                throw new NullPointerException();
            }
            Long mongId = Long.parseLong(mongIdStr);
            FindMongInfoDto findMongInfoDto = mongService.findMongInfo(mongId);
            return ResponseEntity.ok().body(findMongInfoDto);
        } catch (NullPointerException e) {
            log.info("code : {}, message : {}", InformationStateCode.NULL_POINT.getCode(),
                InformationStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(InformationStateCode.NULL_POINT));
        } catch (NotFoundMongException e) {
            log.info("code : {}, message : {}", InformationStateCode.NOT_FOUND.getCode(),
                InformationStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(InformationStateCode.NOT_FOUND));
        }
    }

    @GetMapping("/mong/status")
    public ResponseEntity<Object> findMongStatus(HttpServletRequest httpServletRequest) {
        String mongIdStr = httpServletRequest.getHeader(headerMong);
        try {
            if (mongIdStr == null || mongIdStr.equals("")) {
                throw new NullPointerException();
            }
            Long mongId = Long.parseLong(mongIdStr);
            FindMongStatusDto findMongStatusDto = mongService.findMongStatus(mongId);
            return ResponseEntity.ok().body(findMongStatusDto);
        } catch (NullPointerException e) {
            log.info("code : {}, message : {}", InformationStateCode.NULL_POINT.getCode(),
                InformationStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(InformationStateCode.NULL_POINT));
        } catch (NotFoundMongException e) {
            log.info("code : {}, message : {}", InformationStateCode.NOT_FOUND.getCode(),
                InformationStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(InformationStateCode.NOT_FOUND));
        }
    }

    @GetMapping("/mong/status_battle")
    public ResponseEntity<Object> findMongBattle(MongBattleDto mongBattleDto) {
        try {
            if (mongBattleDto.getMongId() == null) {
                throw new NullPointerException();
            }
            FindMongBattleDto findMongBattleDto = mongService.findMongBattle(
                mongBattleDto.getMongId());
            return ResponseEntity.ok().body(findMongBattleDto);
        } catch (NullPointerException e) {
            log.info("code : {}, message : {}", InformationStateCode.NULL_POINT.getCode(),
                InformationStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(InformationStateCode.NULL_POINT));
        } catch (NotFoundMongException e) {
            log.info("code : {}, message : {}", InformationStateCode.NOT_FOUND.getCode(),
                InformationStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(InformationStateCode.NOT_FOUND));
        }
    }

    @GetMapping("/lastbuy")
    public ResponseEntity<Object> findLastBuy(HttpServletRequest httpServletRequest,
        FindLastBuyReqDto findLastBuyDto) {
        String mongIdStr = httpServletRequest.getHeader(headerMong);
        log.info("foodCode - {} ", findLastBuyDto.getFoodCode());
        try {
            if (mongIdStr == null || mongIdStr.equals("")) {
                throw new NullPointerException();
            }
            Long mongId = Long.parseLong(mongIdStr);
            FindLastBuyResDto findLastBuyResDto = mongService.findLastBuy(mongId,
                findLastBuyDto.getFoodCode());
            log.info("code : {}, message : {}", InformationStateCode.SUCCESS.getCode(),
                InformationStateCode.SUCCESS.name());
            return ResponseEntity.status(HttpStatus.OK).body(findLastBuyResDto);
        } catch (NullPointerException e) {
            log.info("code : {}, message : {}", InformationStateCode.NULL_POINT.getCode(),
                InformationStateCode.NULL_POINT.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(InformationStateCode.NULL_POINT));
        } catch (NotFoundMongException e) {
            log.info("code : {}, message : {}", InformationStateCode.NOT_FOUND.getCode(),
                InformationStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(InformationStateCode.NOT_FOUND));
        }

    }

    @GetMapping("/mong/master")
    public ResponseEntity<Object> findMongMaster(FindMongMasterReqDto findMongMasterReqDto) {
        try {
            FindMongMasterResDto findMongMasterResDto = mongService.findMongMaster(
                findMongMasterReqDto.getMongId());
            log.info("code : {}, message : {}", InformationStateCode.SUCCESS.getCode(),
                InformationStateCode.SUCCESS.name());
            return ResponseEntity.status(HttpStatus.OK).body(findMongMasterResDto);
        } catch (NotFoundMongException e) {
            log.info("code : {}, message : {}", InformationStateCode.NOT_FOUND.getCode(),
                InformationStateCode.NOT_FOUND.name());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(InformationStateCode.NOT_FOUND));
        }
    }
}
