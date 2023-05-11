package com.paymong.battle.global.client;

import com.paymong.battle.battle.dto.request.FindMongBattleReqDto;
import feign.Body;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "information")
public interface InformationServiceClient {
    @GetMapping("/information/mong/status_battle")
    ResponseEntity<Object> findMongBattle(@SpringQueryMap FindMongBattleReqDto findMongBattleReqDto);
}
