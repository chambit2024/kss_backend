package com.emptyseat.kss.global.redis.controller;

import com.emptyseat.kss.global.redis.dto.ChatMessage;
import com.emptyseat.kss.global.redis.service.RedisPubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisController {
    private final RedisPubService redisPubService;

    @PostMapping("api/chat")
    public String pubSub(@RequestBody ChatMessage chatMessage) {
        redisPubService.publishMessage();

        return "success";
    }
}
