package com.emptyseat.kss.global.redis.service;

import com.emptyseat.kss.global.redis.dto.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubService implements MessageListener {
    /**
     * redis에서 날라가는 데이터들을 리스트에 저장. 추후 DB로 바꿔도 됨
     */
    public static List<String> messageList = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // ObjectMapper.readValue를 사용해 JSON을 파싱하여 POJO로 변환
            ChatMessage chatMessage = mapper.readValue(message.getBody(), ChatMessage.class);
            messageList.add(message.toString());

            log.info("받은 메세지 = " + message.toString());
            log.info("chatMessage.getSender() = " + chatMessage.getSender());
            log.info("chatMessage.getContext() = " + chatMessage.getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
