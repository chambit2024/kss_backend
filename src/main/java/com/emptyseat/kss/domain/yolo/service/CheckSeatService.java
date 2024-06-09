package com.emptyseat.kss.domain.yolo.service;

import com.emptyseat.kss.domain.yolo.entity.Box;
import com.emptyseat.kss.domain.yolo.entity.Pos;
import com.emptyseat.kss.domain.yolo.util.BoxUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@Getter
public class CheckSeatService {
    private final List<Box> chairs = new ArrayList<>();
    private final List<Box> peoples = new ArrayList<>();
    private final List<Box> others = new ArrayList<>();
    private final BoxUtil boxUtil = new BoxUtil();
    private final Map<Pos, Integer> seatCount = new HashMap<>();
    private final double seatDistanceThreshold = 0.01; // 기존 자리인지의 여부를 판단하는 절댓값 기준치
    private final double boxCollidingPercentThreshold = 10; // box들 간의 겹침 여부를 판단하는 기준값 (%)


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
        boolean isSeatExist = false;
        Set<Box> checkedChairs = new HashSet<>();
        log.info("[BEFORE] seatCount : " + seatCount);


        for (Box chair: chairs) {
            Pos currentChairPos = new Pos(chair.getCenter_x(), chair.getCenter_y());
            for (Pos seatPos: seatCount.keySet()) {
                if (boxUtil.isSameSeat(currentChairPos, seatPos, seatDistanceThreshold)) {
                    // 기존 좌석 취급. 좌표 업데이트 필요
                    isSeatExist = true;
                    int currentSeatCount = seatCount.get(seatPos);
                    seatCount.remove(seatPos);
                    seatCount.put(currentChairPos, currentSeatCount);
                    log.info("seatCount 업데이트 : " + seatCount.get(currentChairPos));
                    break;
                }
            }
            if (!isSeatExist) {
                seatCount.put(currentChairPos, 0);
            }
            GOTO_PEOPLE:
            for (Box people: peoples) {

                if (boxUtil.overlap_percent(chair, people) > boxCollidingPercentThreshold) {
                    // 사석화 X. seat_count 0으로 초기화
                    seatCount.put(currentChairPos, 0);
                } else {
                    // 사석화 O
                    for (Box other: others) {
                        if ((boxUtil.overlap_percent(chair, other) > boxCollidingPercentThreshold) && !checkedChairs.contains(chair)) {
                            // 사석화 O. seat_count += 1
                            seatCount.put(currentChairPos, seatCount.get(currentChairPos) + 1);
                            checkedChairs.add(chair);
                            break GOTO_PEOPLE;
                        } else {
                            // 사석화 X. seat_count 0으로 초기화
                            seatCount.put(currentChairPos, 0);
                        }
                    }
                }
            }
        }
        log.info("[AFTER] seatCount : " + seatCount);
    }
}
