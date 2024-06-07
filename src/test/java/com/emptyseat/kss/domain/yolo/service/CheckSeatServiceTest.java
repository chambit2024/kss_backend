package com.emptyseat.kss.domain.yolo.service;

import com.emptyseat.kss.domain.yolo.entity.Box;
import com.emptyseat.kss.domain.yolo.entity.LabelType;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckSeatServiceTest {

    List<Box> boxList = List.of(
            new Box(LabelType.LAPTOP, 0.447135, 0.398148, 0.0442708, 0.103704),
            new Box(LabelType.BOTTLE, 0.696354, 0.45787, 0.025, 0.106481),
            new Box(LabelType.CHAIR, 0.0828125, 0.681944, 0.0697917, 0.252778),
            new Box(LabelType.PEOPLE, 0.624219, 0.478241, 0.0984375, 0.323148),
            new Box(LabelType.BAG, 0.739583, 0.490741, 0.0510417, 0.0666667),
            new Box(LabelType.CHAIR, 0.53776, 0.771759, 0.132812, 0.206481),
            new Box(LabelType.CHAIR, 0.341667, 0.472685, 0.0708333, 0.173148),
            new Box(LabelType.CHAIR, 0.616667, 0.565741, 0.101042, 0.155556),
            new Box(LabelType.CHAIR, 0.434115, 0.541667, 0.0838542, 0.237037)
    );

    @Test
    void CheckSeatService_박스분류확인() {
        CheckSeatService checkSeatService = new CheckSeatService(boxList);

        assertEquals(checkSeatService.getChairs().size(), 5, "chairs 개수 불일치");
        assertEquals(checkSeatService.getPeoples().size(), 1, "peoples 개수 불일치");
        assertEquals(checkSeatService.getOthers().size(), 3, "others 개수 불일치");
    }
}