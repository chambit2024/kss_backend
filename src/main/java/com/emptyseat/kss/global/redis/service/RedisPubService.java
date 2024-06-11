package com.emptyseat.kss.global.redis.service;

import com.emptyseat.kss.global.redis.dto.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisPubService {

//    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private int index = 1;

//    public RedisPubService(@Qualifier("pubsubRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
    public RedisPubService(@Qualifier("stringRedisTemplate") StringRedisTemplate redisTemplate) {
        this.stringRedisTemplate = redisTemplate;
    }

//    public void sendMessage(ChatMessage chatMessage) {
//        redisTemplate.convertAndSend("yolo_result", chatMessage);
//    }

    @Async
    @Scheduled(fixedRate = 1000)
    public void publishMessage() {
        String mockFilePath = "{\"filePath\":"+"\"chambit_test_mini_" + (index++) + ".txt\"" + "}";
        log.info("Published message: " + mockFilePath);
        stringRedisTemplate.convertAndSend("yolo_result", mockFilePath);
    }
}