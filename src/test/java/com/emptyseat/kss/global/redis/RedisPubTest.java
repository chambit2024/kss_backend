package com.emptyseat.kss.global.redis;


import com.emptyseat.kss.KssApplication;
import com.emptyseat.kss.global.redis.service.RedisPubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig
@EnableScheduling
@SpringBootTest
@ContextConfiguration(classes = KssApplication.class)
public class RedisPubTest {

    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @InjectMocks
    private RedisPubService redisPubService;

    private int index = 0;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPublishMessage() {
        redisPubService.publishMessage();
        String mockFilePath = "{\"filePath\":"+"\"chambit_test_mini_" + (index++) + ".txt\"" + "}";
//        verify(stringRedisTemplate, times(1)).convertAndSend("yolo_result", mockFilePath);
    }
}
