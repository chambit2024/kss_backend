package com.emptyseat.kss.global.redis.service;

import com.emptyseat.kss.global.redis.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisPubService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisPubService(@Qualifier("pubsubRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessage(ChatMessage chatMessage) {
        redisTemplate.convertAndSend("yolo_result", chatMessage);
    }
}