package com.emptyseat.kss.domain.yolo.service;

import com.emptyseat.kss.domain.yolo.entity.Box;
import com.emptyseat.kss.domain.yolo.util.BoxUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@Getter
public class CheckSeatService {
    private final List<Box> chairs = new ArrayList<>();
    private final List<Box> peoples = new ArrayList<>();
    private final List<Box> others = new ArrayList<>();

    private final BoxUtil boxUtil = new BoxUtil();

    // TODO: count 기록하는 기능 추가

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

    public void FindHoggedSeat() {
        for (Box chair: chairs) {
            for (Box people: peoples) {
                if (boxUtil.isColliding(chair, people)) {
                    // 사석화 X. seat_count 0으로 초기화
                } else {
                    // 사석화 O
                    for (Box other: others) {
                        if (boxUtil.isColliding(chair, other)) {
                            // 사석화 O. seat_count += 1
                            break;
                        } else {
                            // 사석화 X. seat_count 0으로 초기화
                        }
                    }
                }
            }
        }
    }
}
