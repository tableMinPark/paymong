package com.paymong.battle.controller;

import com.paymong.battle.dto.request.BattleReadyRequest;
import com.paymong.battle.global.code.BattleStateCode;
import com.paymong.battle.global.exception.NotFoundException;
import com.paymong.battle.global.response.BasicResponse;
import com.paymong.battle.service.BattleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/battle")
public class BattleController {
    private final BattleService battleService;

    @PostMapping("/ready")
    public ResponseEntity<BasicResponse> battleReady(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody BattleReadyRequest battleReadyRequest) {

        try {
            accessToken = accessToken.substring(7);
            Long userId = battleReadyRequest.getMemberId();

            battleService.battleReady(accessToken, userId);
            return ResponseEntity.ok().body(BasicResponse.Body(BattleStateCode.SUCCESS, null));
        } catch (NotFoundException e){
            return ResponseEntity.badRequest().body(BasicResponse.Body(BattleStateCode.FAIL, null));
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(BasicResponse.Body(BattleStateCode.FAIL, null));
        }
    }
}
