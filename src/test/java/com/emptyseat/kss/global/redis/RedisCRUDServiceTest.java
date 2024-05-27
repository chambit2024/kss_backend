package com.emptyseat.kss.global.redis;

import com.emptyseat.kss.KssApplication;
import com.emptyseat.kss.global.redis.service.RedisCRUDService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.Duration;

import static org.assertj.core.api.Assertions.*;
import static org.awaitility.Awaitility.await;


@Slf4j
@SpringBootTest
@ContextConfiguration(classes = KssApplication.class)
class RedisCRUDServiceTest {
    final String KEY = "key";
    final String VALUE = "value";
    final Duration DURATION = Duration.ofMillis(5000);
    @Autowired
    private RedisCRUDService redisCRUDService;

    @BeforeEach
    void shutDown() {
        redisCRUDService.setValues(KEY, VALUE, DURATION);
    }

    @AfterEach
    void tearDown() {
        redisCRUDService.deleteValues(KEY);
    }

    @Test
    @DisplayName("Redis에 데이터를 저장하면 정상적으로 조회된다.")
    void saveAndFindTest() throws Exception {
        // when
        String findValue = redisCRUDService.getValues(KEY);

        // then
        assertThat(VALUE).isEqualTo(findValue);
    }

    @Test
    @DisplayName("Redis에 저장된 데이터를 수정할 수 있다.")
    void updateTest() throws Exception {
        // given
        String updateValue = "updateValue";
        redisCRUDService.setValues(KEY, updateValue, DURATION);

        // when
        String findValue = redisCRUDService.getValues(KEY);

        // then
        assertThat(updateValue).isEqualTo(findValue);
        assertThat(VALUE).isNotEqualTo(findValue);
    }

    @Test
    @DisplayName("Redis에 저장된 데이터를 삭제할 수 있다.")
    void deleteTest() throws Exception {
        // when
        redisCRUDService.deleteValues(KEY);
        String findValue = redisCRUDService.getValues(KEY);

        // then
        assertThat(findValue).isEqualTo("false");
    }

    @Test
    @DisplayName("Redis에 저장된 데이터는 만료시간이 지나면 삭제된다.")
    void expiredTest() throws Exception {
        // when
        String findValue = redisCRUDService.getValues(KEY);
        await().pollDelay(Duration.ofMillis(6000)).untilAsserted(
                () -> {
                    String expiredValue = redisCRUDService.getValues(KEY);
                    assertThat(expiredValue).isNotEqualTo(findValue);
                    assertThat(expiredValue).isEqualTo("false");
                }
        );
    }
}