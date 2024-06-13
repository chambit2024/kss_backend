package com.emptyseat.kss.global.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisCRUDService {
    private static StringRedisTemplate redisTemplate = new StringRedisTemplate();

    public RedisCRUDService(@Qualifier("crudRedisTemplate") StringRedisTemplate redisTemplate) {
        RedisCRUDService.redisTemplate = redisTemplate;
    }

    /**
     * key와 data를 Redis에 저장.
     */
    public static void setValues(String key, String data) {
        ValueOperations<String, String> values= redisTemplate.opsForValue();
        values.set(key, data);
    }

    /**
     * key와 data를 Redis에 저장. 만약 데이터에 만료 시간을 설정하고 싶으면 Duration 객체 전달
     */
    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, String> values= redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    /**
     * key 파라미터로 받아 key를 기반으로 데이터를 조회한다.
     */
    @Transactional(readOnly = true)
    public static String getValues(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        if (values.get(key) == null) {
            return "false";
        }
        return String.valueOf(values.get(key));
    }

    /**
     * key를 파라미터로 받아 key를 기반으로 데이터를 삭제한다.
     */
    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

    public void expireValues(String key, int timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    public void setHashOps(String key, Map<String, String> data) {
        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
        values.putAll(key, data);
    }

    @Transactional(readOnly = true)
    public String getHashOps(String key, String hashKey) {
        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
        return Boolean.TRUE.equals(values.hasKey(key, hashKey)) ? String.valueOf(redisTemplate.opsForHash().get(key, hashKey)) : "";
    }

    public void deleteHashOps(String key, String hashKey) {
        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
        values.delete(key, hashKey);
    }

    /**
     * 조회하려는 데이터가 없으면 "false"를 반환
     */
    public boolean checkExistsValue(String value) {
        return !value.equals("false");
    }
}
