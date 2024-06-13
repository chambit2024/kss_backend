package com.emptyseat.kss.domain.yolo.util;

import com.emptyseat.kss.domain.yolo.entity.Box;
import com.emptyseat.kss.domain.yolo.entity.LabelType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxUtilTest {

    BoxUtil boxUtil = new BoxUtil();
    Box chair = new Box(LabelType.CHAIR, 0, 0, 100, 100);
    Box people = new Box(LabelType.PEOPLE, 50, 50, 100, 100);
    Box bag = new Box(LabelType.BAG,200, 200, 50, 50);
    Box mockdata1 = new Box(LabelType.CHAIR, 0.0828125, 0.681944, 0.0697917, 0.252778);
    Box mockdata2 = new Box(LabelType.PEOPLE, 0.624219, 0.478241, 0.0984375, 0.323148);

    @Test
    void Box_충돌인지여부확인() {
        assertTrue(boxUtil.isColliding(chair, people), "Chair and People should be colliding.");
        assertFalse(boxUtil.isColliding(people, bag), "People and Bag should not be colliding.");
        // TODO : mock 데이터로 측정한 결과 사람과 의자 사이에 겹침이 없었다. 해당 프레임이 이상한건지 로직이 이상한건지 확인 필요
        assertTrue(boxUtil.isColliding(mockdata1, mockdata2), "mock CHAIR and mock PEOPLE should be colliding");
    }

    @Test
    void Box_겹치는정도계산() {
        double overlapPercent1 = boxUtil.overlap_percent(chair, people);
        double expectedOverlapArea1 = (50 * 50) / 10000.0;
        // delta는 오차 허용값을 의미
        assertEquals(expectedOverlapArea1, overlapPercent1, 0.0001, "Overlap percent between Chair and People should be correct.");

        double overlapPercent2 = boxUtil.overlap_percent(chair, bag);
        assertEquals(0.0, overlapPercent2, "Overlap percent between Chair and Bag should be 0.");
    }

    @Test
    void Box_샘플좌표계산() {
        Box box1 = new Box("4", "0.447917", "0.406944", "0.0458333", "0.0824074");
        Box box2 = new Box("3", "0.429167", "0.55787", "0.09375", "0.25463");

        System.out.println(boxUtil.overlap_percent(box1, box2));
    }
}