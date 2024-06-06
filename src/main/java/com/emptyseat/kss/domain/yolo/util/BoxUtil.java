package com.emptyseat.kss.domain.yolo.util;

import com.emptyseat.kss.domain.yolo.entity.Box;
import com.emptyseat.kss.domain.yolo.service.CheckSeatService;

public class BoxUtil {

    public boolean isColliding(Box box1, Box box2) {
        // 아래 조건들 중 하나라도 해당 시 충돌 X
        return !(box1.getCenter_x() + box1.getWidth()/2 < box2.getCenter_x() - box2.getWidth()/2
                        || box1.getCenter_y() + box1.getHeight()/2 < box2.getCenter_y() - box2.getHeight()/2
                        || box2.getCenter_x() + box2.getWidth()/2 < box1.getCenter_x() - box1.getWidth()/2
                        || box2.getCenter_y() + box2.getHeight()/2 < box1.getCenter_y() - box1.getHeight()/2
        );
    }

    /**
     * box1과 box2와의 겹침 정도를 계산
     */
    public double overlap_percent(Box box1, Box box2) {
        // (겹치는 부분) / (더 큰 박스)
        double baseArea = box1.isBigger(box2) ? box1.boxSize() : box2.boxSize();
        double overlapArea;
        if (isColliding(box1, box2)) {
            // 충돌했을 경우
            double xOverlap = Math.min(box1.getCenter_x() + box1.getWidth()/2, box2.getCenter_x() + box2.getWidth()/2)
                    - Math.max(box1.getCenter_x() - box1.getWidth()/2, box2.getCenter_x() - box2.getWidth()/2);
            double yOverlap = Math.min(box1.getCenter_y() + box1.getHeight() / 2, box2.getCenter_y() + box2.getHeight() / 2)
                    - Math.max(box1.getCenter_y() - box1.getHeight()/2, box2.getCenter_y() - box2.getHeight()/2);
            overlapArea = xOverlap * yOverlap;
        }
        else overlapArea = 0;

        return overlapArea / baseArea;
    }

}
