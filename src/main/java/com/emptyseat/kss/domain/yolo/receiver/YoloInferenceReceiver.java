package com.emptyseat.kss.domain.yolo.receiver;

import com.emptyseat.kss.domain.yolo.dto.YoloMessageRequest;
import com.emptyseat.kss.domain.yolo.entity.Pos;
import com.emptyseat.kss.domain.yolo.service.CheckSeatService;
import com.emptyseat.kss.domain.yolo.service.FileProcessingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


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
    private final Map<Pos, Integer> seatCount = new HashMap<>();


    private String labelStoragePath = "/labels/";

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // ObjectMapper.readValue를 사용해 JSON을 파싱하여 POJO로 변환
            YoloMessageRequest chatMessage = mapper.readValue(message.getBody(), YoloMessageRequest.class);

            String filePath = labelStoragePath + chatMessage.getFilePath();
            log.info("현재 파일 path: " + filePath);
            fileProcessingService.processFile(filePath);

            log.info("현재 파일 boxlist: " + fileProcessingService.getBoxList());

            CheckSeatService checkSeatService = new CheckSeatService(fileProcessingService.getBoxList());
            checkSeatService.FindHoggedSeat(seatCount);

            log.info("누적 seatCount: " + seatCount);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
