package com.paymong.battle.global.redis;


import com.paymong.battle.battle.dto.common.BattleUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MatchingRepository {
    private RedisTemplate redisTemplate;

    public MatchingRepository( @Qualifier("matchingRedisTemplate") RedisTemplate redisTemplate ){
        this.redisTemplate = redisTemplate;
    }

    public void save(Long characterId) {

    }

    public Optional<BattleUser> findById(Long characterId) {
        BattleUser battleUser = null;

        return Optional.ofNullable(battleUser);
    }

    public void remove(Long characterId) {

    }
}
