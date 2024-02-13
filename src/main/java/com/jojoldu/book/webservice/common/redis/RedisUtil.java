package com.jojoldu.book.webservice.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void setData(String key, String value, Long expiredTime) {
        // 데이터 저장 시 만료 시간 지정하기 위해 밀리 초로 지정
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
    }
}
