package com.paymong.management.global.client;


import com.paymong.management.global.dto.CommonCodeDto;
import com.paymong.management.global.dto.FindCommonCodeDto;
import com.paymong.management.global.dto.FindMongLevelCodeDto;
import com.paymong.management.mong.dto.FindRandomEggDto;
import com.paymong.management.status.dto.FindStatusReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "common")
public interface CommonServiceClient {
//    @GetMapping("/common/egg")
//    public FindRandomEggDto findRandomEgg() throws RuntimeException;

    @GetMapping("/common/status/detail")
    public ResponseEntity<Object> findStatus(@SpringQueryMap FindStatusReqDto findStatusReqDto);

    @GetMapping("/common/detail")
    public ResponseEntity<Object> findCommonCode(@SpringQueryMap FindCommonCodeDto findCommonCodeDto);

    @GetMapping("/common/level")
    public ResponseEntity<Object> findMongLevelCode(@SpringQueryMap FindMongLevelCodeDto findMongLevelCodeDto);
}
