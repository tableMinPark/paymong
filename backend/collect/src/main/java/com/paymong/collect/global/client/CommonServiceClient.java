package com.paymong.collect.global.client;

import com.paymong.collect.global.vo.response.MapCodeResVo;
import com.paymong.collect.global.vo.response.MongCodeResVo;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "COMMON")
public interface CommonServiceClient {

    @GetMapping("/common/mong")
    List<MongCodeResVo> findAllMongCode();

    @GetMapping("/common/map")
    List<MapCodeResVo> findAllMapCode();
}
