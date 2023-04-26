package com.paymong.battle.battle.controller;

import com.paymong.battle.battle.dto.response.AuthResDto;
import com.paymong.battle.battle.dto.response.BattleReadyByLocationResDto;
import com.paymong.battle.battle.service.AuthService;
import com.paymong.battle.battle.service.BattleService;
import com.paymong.battle.battle.dto.request.BattleReadyByLocationReqDto;
import com.paymong.battle.battle.vo.request.BattleReadyByLocationReqVo;
import com.paymong.battle.battle.vo.response.BattleReadyByLocationResVo;
import com.paymong.battle.global.code.BattleStateCode;
import com.paymong.battle.global.exception.NotFoundException;
import com.paymong.battle.global.response.ErrorResponse;
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
    private final AuthService authService;

    @PostMapping("/location")
    public ResponseEntity<Object> battleReadyByLocation(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody BattleReadyByLocationReqDto battleReadyByLocationReqDto) {

        log.info("battleReadyByLocation - Call");

        try {
            accessToken = accessToken.substring(7);

            AuthResDto authResDto = authService.findMemberInfo(accessToken);

            BattleReadyByLocationResVo battleReadyByLocationResVo
                    = battleService.battleReadyByLocation(BattleReadyByLocationReqVo.builder()
                    .characterId(authResDto.getCharacterId())
                    .latitude(battleReadyByLocationReqDto.getLatitude())
                    .longitude(battleReadyByLocationReqDto.getLongitude())
                    .build());

            return ResponseEntity.ok().body(
                    BattleReadyByLocationResDto.builder()
                            .battleRoomId(battleReadyByLocationResVo.getBattleRoomId())
                            .name(battleReadyByLocationResVo.getName())
                            .order(battleReadyByLocationResVo.getOrder())
                            .build());

        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponse(BattleStateCode.FAIL));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new ErrorResponse(BattleStateCode.FAIL));
        }
    }
}