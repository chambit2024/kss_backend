package com.emptyseat.kss.domain.yolo.receiver;

import com.emptyseat.kss.domain.yolo.dto.YoloMessageRequest;
import com.emptyseat.kss.domain.yolo.entity.Box;
import com.emptyseat.kss.domain.yolo.entity.Pos;
import com.emptyseat.kss.domain.yolo.service.CheckSeatService;
import com.emptyseat.kss.domain.yolo.service.FileProcessingService;
import com.emptyseat.kss.global.redis.service.RedisCRUDService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.*;


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

    private final Set<Box> hoggedSeatSet = new HashSet<>();

    private String labelStoragePath = "/labels/";
    private String DlabelStoragePath = "/Users/seungho/Projects/kss_backend/src/main/resources/mock/";

    public static Map<Integer, Integer> analyzeSeatCount(Map<Pos, Integer> seatCount) {
        Map<Integer, Integer> countMap = new HashMap<>();

        for (Integer value : seatCount.values()) {
            countMap.put(value, countMap.getOrDefault(value, 0) + 1);
        }

        return countMap;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // ObjectMapper.readValue를 사용해 JSON을 파싱하여 POJO로 변환
            YoloMessageRequest chatMessage = mapper.readValue(message.getBody(), YoloMessageRequest.class);

            String filePath = labelStoragePath + chatMessage.getFilePath();
            log.trace("현재 파일 path: " + filePath);

            fileProcessingService.clearBoxList();
            fileProcessingService.processFile(filePath);

            log.trace("현재 파일 boxlist: " + fileProcessingService.getBoxList());

            CheckSeatService checkSeatService = new CheckSeatService(fileProcessingService.getBoxList());
            checkSeatService.FindHoggedSeat(seatCount, hoggedSeatSet);

            log.info("누적 seatCount: " + seatCount);
            Map<Integer, Integer> result = analyzeSeatCount(seatCount);

            for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
                log.info("Count: " + entry.getKey() + ", 개수: " + entry.getValue());
            }


            saveHoggedSeatSetToRedis(hoggedSeatSet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveHoggedSeatSetToRedis(Set<Box> hoggedSeatSet) {
        try {
//            String hoggedSeatJson = mapper.writeValueAsString(hoggedSeatSet);
            String hoggedSeatJson = hoggedSeatSet.toString();
            RedisCRUDService.setValues("latest-hoggedseat", hoggedSeatJson);
            log.info("[redis-latest-hoggedseat] save:  " + RedisCRUDService.getValues("latest-hoggedseat"));
        } catch (Exception e) {
            log.error("Failed to convert hoggedSeatSet to JSON", e);
        }
    }

}
