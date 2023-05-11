package com.paymong.battle.global.client;

import com.paymong.battle.battle.dto.request.FindMongBattleReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "information")
public interface InformationServiceClient {
    @GetMapping("/mong/status_battle")
    ResponseEntity<Object> findMongBattle(FindMongBattleReqDto findMongBattleReqDto);
}
