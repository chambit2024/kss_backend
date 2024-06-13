package com.emptyseat.kss.domain.yolo.service;

import com.emptyseat.kss.domain.yolo.dto.HoggedSeatResponse;
import com.emptyseat.kss.domain.yolo.entity.Box;
import com.emptyseat.kss.domain.yolo.entity.BoxWithoutLabel;
import com.emptyseat.kss.global.redis.service.RedisCRUDService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Getter
@NoArgsConstructor
public class CurrentHoggedSeatService {
    public HoggedSeatResponse getResponse() {
        String rawHoggedSeat = RedisCRUDService.getValues("latest-hoggedseat");
        String rawBlobData = RedisCRUDService.getValues("latest-frame");
        log.info("rawhoggedSeat: " + rawHoggedSeat);
//        log.info("rawBlobData: " + rawBlobData);

        List<BoxWithoutLabel> seats;
        String frame = "hello";

        // 1. 데이터 포맷을 JSON으로 변환
        String jsonData = rawHoggedSeat.replaceAll("Box", "")
                .replaceAll("labelType=[^,]+,", "")
                .replaceAll("center_x=", "\"x\":")
                .replaceAll("center_y=", "\"y\":")
                .replaceAll("width=", "\"w\":")
                .replaceAll("height=", "\"h\":")
                .replaceAll("=", ":")
                .replaceAll("'", "\"");

        // 2. JSON 포맷을 리스트로 변환
        ObjectMapper mapper = new ObjectMapper();
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, BoxWithoutLabel.class);

        List<BoxWithoutLabel> boxes = new ArrayList<>();
        try {
            boxes = mapper.readValue(jsonData, listType);
//            boxes.forEach(System.out::println);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return new HoggedSeatResponse(boxes, rawBlobData);


    }
}