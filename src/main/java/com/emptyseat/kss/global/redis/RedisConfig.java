package com.emptyseat.kss.global.redis;

import com.emptyseat.kss.domain.yolo.dto.YoloMessageRequest;
import com.emptyseat.kss.domain.yolo.receiver.YoloInferenceReceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@RequiredArgsConstructor
@Configuration
@EnableRedisRepositories
public class RedisConfig {
    private final RedisProperties redisProperties;

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.database.pubsub}")
    private int pubsubIndex;

    @Value("${spring.data.redis.database.crud}")
    private int crudIndex;

    @Bean
    @Primary
    public RedisConnectionFactory pubsubConnectionFactory() {
        return createConnectionFactoryWith(pubsubIndex);
    }

    @Bean
    @Qualifier("CRUD")
    public RedisConnectionFactory crudConnectionFactory() {
        return createConnectionFactoryWith(crudIndex);
    }

    /**
     * Serializer 설정으로 redis-cli를 통해 직접 데이터 조회하는 RedisTemplate 설정
     */
    @Bean
    public RedisTemplate<String, Object> pubsubRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(YoloMessageRequest.class));
        //TODO: 수상해 여기 (pubsubConnectionFactory()로 바로 지정하는게 맞나..?
        redisTemplate.setConnectionFactory(pubsubConnectionFactory());

        return redisTemplate;
    }

    /**
     * Serializer 설정으로 redis-cli를 통해 직접 데이터 조회하는 RedisTemplate 설정
     */
    @Bean
    public StringRedisTemplate crudRedisTemplate(@Qualifier("CRUD") RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    /**
     * RedisProperties로 application.properties에 저장된 host, port, database index 연결
     */
    public LettuceConnectionFactory createConnectionFactoryWith(int index) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setDatabase(index);

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public YoloInferenceReceiver yoloInferenceReceiver() {
        return new YoloInferenceReceiver();
    }


    /**
     * 스프링에서 비동기 메세지를 지원하는 마지막 컴포넌트. 정해진 채널로 들어온 메세지를 처리할 action 정의
     */
    @Bean
    MessageListenerAdapter messageListenerAdapter(YoloInferenceReceiver yoloInferenceReceiver) {
        return new MessageListenerAdapter(yoloInferenceReceiver, "onMessage");
    }

    /**
     * 컨테이너 설정
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    RedisMessageListenerContainer redisContainer(MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(pubsubConnectionFactory());
        container.addMessageListener(messageListenerAdapter, topic());
        return container;
    }

    /**
     * pub,sub 토픽 설정
     */
    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("yolo_result");
    }
}
