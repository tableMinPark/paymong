package com.paymong.management.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate redisTemplate;

    //키 - 죽음 - 벨류
    public void setDeath(Long mongId){
        SetOperations<String, ValueOperations<Long, RedisMong>> values = redisTemplate.opsForSet();
        ValueOperations<Long, RedisMong> value = redisTemplate.opsForValue();
        RedisMong mong = new RedisMong();
        mong.setMongId(mongId);
        value.set(mongId, mong, Duration.ofSeconds(10L));

        values.add("death", value);
    }
}
