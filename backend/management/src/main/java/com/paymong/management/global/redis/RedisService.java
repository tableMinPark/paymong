package com.paymong.management.global.redis;

import com.paymong.management.global.scheduler.dto.SchedulerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate redisTemplate;

    //키 - 죽음 - 벨류
    public void addRedis(Map<Long, SchedulerDto> schedulerMap, String type){
        SetOperations<String, RedisMong> values = redisTemplate.opsForSet();

        Set<Long> set = schedulerMap.keySet();

        for (Long key:
             set) {
            SchedulerDto schedulerDto = schedulerMap.get(key);
            Duration diff = Duration.between(schedulerDto.getStartTime(), LocalDateTime.now());
            Long expire = schedulerDto.getExpire() - diff.toSeconds();
            RedisMong redisMong = RedisMong.builder()
                    .mongId(key)
                    .expire(expire)
                    .build();
            values.add(type, redisMong);
        }

//        schedulerMap.entrySet().stream()
//                .forEach(s -> {
//                    Long key = s.getKey();
//                    SchedulerDto scheduler = s.getValue();
//
//                    Duration diff = Duration.between(scheduler.getStartTime(), LocalDateTime.now());
//                    Long expire = scheduler.getExpire() - diff.toSeconds();
//                    RedisMong redisMong = RedisMong.builder()
//                            .mongId(key)
//                            .expire(expire)
//                            .build();
//                    values.add(type, redisMong);
//                });
    }

    public Set<RedisMong> getRedisMong(String key){
        SetOperations values = redisTemplate.opsForSet();
        Set<RedisMong> set = values.members(key);

        Long cnt = values.size(key);
        values.pop(key, cnt);

        return set;
    }
}
