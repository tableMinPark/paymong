package com.paymong.auth.global.redis;

import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}
