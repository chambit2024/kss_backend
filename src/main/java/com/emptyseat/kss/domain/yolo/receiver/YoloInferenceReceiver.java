package com.emptyseat.kss.domain.yolo.receiver;

import com.emptyseat.kss.domain.yolo.dto.YoloMessageRequest;
import com.emptyseat.kss.domain.yolo.service.CheckSeatService;
import com.emptyseat.kss.domain.yolo.service.FileProcessingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Arrays;


@Slf4j
@Service
@RequiredArgsConstructor
public class YoloInferenceReceiver implements MessageListener {
    /**
     * redis에서 수신하는 파일 경로들 리스트
     */
//    public static List<YoloMessageRequest> filePathList = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final FileProcessingService fileProcessingService = new FileProcessingService();


    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // ObjectMapper.readValue를 사용해 JSON을 파싱하여 POJO로 변환
            YoloMessageRequest chatMessage = mapper.readValue(message.getBody(), YoloMessageRequest.class);
            String receivedFilePath = Arrays.toString(message.getBody());
            log.info("receivedFilePath = " + receivedFilePath);
            log.info("chatMessage.getFilePath() = " + chatMessage.getFilePath());

            fileProcessingService.processFile(chatMessage.getFilePath());

            log.info("현재 파일 boxlist: " + fileProcessingService.getBoxList());

            CheckSeatService checkSeatService = new CheckSeatService(fileProcessingService.getBoxList());
            checkSeatService.FindHoggedSeat();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
