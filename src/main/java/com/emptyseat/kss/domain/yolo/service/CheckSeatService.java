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
//    private final Map<Pos, Integer> seatCount = new HashMap<>();
    private final double seatDistanceThreshold = 0.1; // 기존 자리인지의 여부를 판단하는 절댓값 기준치
    private final double boxCollidingPercentThreshold = 3; // box들 간의 겹침 여부를 판단하는 기준값 (%)
    private final double seatCountThreshold = 4; // 사석화 판정 기준 임계치


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

    public void FindHoggedSeat(Map<Pos, Integer> seatCount, Set<Box> hoggedSeatSet) {
        boolean isSeatExist;

        log.trace("list[CHAIRS]: "+chairs.size()+"개: " + chairs);
        log.trace("list[PEOPLES]: "+peoples.size()+"개: " + peoples);
        log.trace("list[OTHERS]: "+others.size()+"개: " + others);


        for (Box chair: chairs) {
            isSeatExist = false;
            Pos currentChairPos = new Pos(chair.getCenter_x(), chair.getCenter_y());

            Iterator<Map.Entry<Pos, Integer>> iterator = seatCount.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<Pos, Integer> entry = iterator.next();
                Pos seatPos = entry.getKey();
                if ((Objects.equals(seatPos.getX(), currentChairPos.getX()) && Objects.equals(seatPos.getY(), currentChairPos.getY()))
                        || boxUtil.isSameSeat(currentChairPos, seatPos, seatDistanceThreshold)) {
                    isSeatExist = true;
                    int currentSeatCountValue = entry.getValue();
                    iterator.remove();
                    seatCount.put(currentChairPos, currentSeatCountValue);
                    log.trace("key change: " + seatPos + " -> " + currentChairPos + ", value=" + currentSeatCountValue);
                    break;
                }
            }

            if (!isSeatExist) {
                seatCount.put(currentChairPos, 0);
            }
            LOOP_PEOPLE:
            for (Box people: peoples) {

                if (boxUtil.overlap_percent(chair, people) > boxCollidingPercentThreshold) {
                    // 사석화 X. seat_count 0으로 초기화
                    int prevPosSeatCount = seatCount.getOrDefault(currentChairPos, 0);
                    seatCount.put(currentChairPos, 0);
                    if (prevPosSeatCount > 0) {
                        log.info("seatCount <key:" + currentChairPos +"> 초기화 by [chair, people]: " + seatCount.get(currentChairPos));
                        for (Box hoggedSeat: hoggedSeatSet) {
                            if (boxUtil.isSameSeat(hoggedSeat, chair, 0.05)) {
                                hoggedSeatSet.remove(hoggedSeat);
                                break;
                            }
                        }
                    }
                } else {
                    // 사석화 O
                    for (Box other: others) {
                        int nextSeatCount = seatCount.getOrDefault(currentChairPos, 9) + 1;
                        if (boxUtil.overlap_percent(chair, other) > boxCollidingPercentThreshold) {
                            // 사석화 O. seat_count += 1
                            seatCount.put(currentChairPos, nextSeatCount);
                            log.info("seatCount <key:" + currentChairPos + "> 업데이트 : " + seatCount.get(currentChairPos));
                            if (nextSeatCount == seatCountThreshold) {
                                hoggedSeatSet.add(chair);
                            }
                            break LOOP_PEOPLE;
                        }
                    }
                    // 사석화 X. seat_count 0으로 초기화
                    int prevChairPos = seatCount.getOrDefault(currentChairPos, 0);
                    seatCount.put(currentChairPos, 0);
                    if (prevChairPos > 0) {
                        log.info("seatCount <key:" + currentChairPos +"> 초기화 by [only chair]: " + seatCount.get(currentChairPos));
                        for (Box hoggedSeat: hoggedSeatSet) {
                            if (boxUtil.isSameSeat(hoggedSeat, chair, 0.05)) {
                                hoggedSeatSet.remove(hoggedSeat);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
