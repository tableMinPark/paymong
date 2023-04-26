package com.paymong.battle.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.cache.host}")
    private String cacheHost;
    @Value("${spring.redis.cache.port}")
    private Integer cachePort;
    @Value("${spring.redis.cache.password}")
    private String cachePassword;

    // 레디스 커넥션
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(cacheHost);
        redisStandaloneConfiguration.setPort(cachePort);
        redisStandaloneConfiguration.setPassword(cachePassword);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    // 대기열 레디스 템플릿 생성
    @Bean
    public RedisTemplate<String, Object> matchingRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    // GPS 레디스 템플릿 생성
    @Bean
    public RedisTemplate<String, Object> locationRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
