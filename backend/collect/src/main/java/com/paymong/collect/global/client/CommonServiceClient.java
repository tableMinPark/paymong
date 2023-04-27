package com.paymong.collect.global.client;

import com.paymong.collect.global.vo.response.CommonCodeResVo;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "COMMON")
public interface CommonServiceClient {

    @GetMapping("/common/mong")
    List<CommonCodeResVo> findAllMongCode();

    @GetMapping("/common/map")
    List<CommonCodeResVo> findAllMapCode();
}
