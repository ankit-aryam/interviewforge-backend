package com.interviewforge.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimitService {
    private final StringRedisTemplate redisTemplate;

    private static final int LIMIT = 5;
    private static final int WINDOW = 60;

    public boolean isAllowed(String key){
        String redisKey = "rate_limit:"+key;

        Long count = redisTemplate.opsForValue().increment(redisKey);

        if (count == 1) {
            redisTemplate.expire(redisKey, Duration.ofSeconds(WINDOW));
        }

        return count<= LIMIT;
    }
}
