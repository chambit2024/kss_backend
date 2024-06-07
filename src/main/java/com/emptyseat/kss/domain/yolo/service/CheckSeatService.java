package com.emptyseat.kss.domain.yolo.service;

import com.emptyseat.kss.domain.yolo.entity.Box;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Getter
public class CheckSeatService {
    private final List<Box> chairs = new ArrayList<>();
    private final List<Box> peoples = new ArrayList<>();
    private final List<Box> others = new ArrayList<>();

    public CheckSeatService(List<Box> boxList) {
        for (Box box: boxList) {
            // boxes 안의 값들을 CHAIR, PEOPLE, OTHERS로 분류
            switch (box.getLabelType()) {
                case CHAIR -> chairs.add(box);
                case PEOPLE -> peoples.add(box);
                default -> others.add(box);
            }
        }
    }
}
