package com.emptyseat.kss.domain.yolo.service;

import com.emptyseat.kss.domain.yolo.entity.Box;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CheckSeatServiceTest {

    List<Box> boxList = List.of(
//            new Box(LabelType.LAPTOP, 0.447135, 0.398148, 0.0442708, 0.103704),
//            new Box(LabelType.BOTTLE, 0.696354, 0.45787, 0.025, 0.106481),
//            new Box(LabelType.CHAIR, 0.0828125, 0.681944, 0.0697917, 0.252778),
//            new Box(LabelType.PEOPLE, 0.624219, 0.478241, 0.0984375, 0.323148),
//            new Box(LabelType.BAG, 0.739583, 0.490741, 0.0510417, 0.0666667),
//            new Box(LabelType.CHAIR, 0.53776, 0.771759, 0.132812, 0.206481),
//            new Box(LabelType.CHAIR, 0.341667, 0.472685, 0.0708333, 0.173148),
//            new Box(LabelType.CHAIR, 0.616667, 0.565741, 0.101042, 0.155556),
//            new Box(LabelType.CHAIR, 0.434115, 0.541667, 0.0838542, 0.237037)
            new Box("4", "0.448958", "0.406481", "0.0489583", "0.116667"),
            new Box("2", "0.696875", "0.45787", "0.025", "0.106481"),
            new Box("6", "0.624479", "0.478704", "0.0979167", "0.322222"),
            new Box("3", "0.0830729", "0.681019", "0.0734375", "0.260185"),
            new Box("0", "0.739844", "0.491204", "0.0505208", "0.0675926"),
            new Box("3", "0.616406", "0.565278", "0.100521", "0.156481"),
            new Box("3", "0.341406", "0.471759", "0.0703125", "0.173148"),
            new Box("3", "0.536979", "0.771296", "0.132292", "0.203704"),
            new Box("3", "0.433594", "0.541667", "0.0838542", "0.238889"),
            new Box("3", "0.710938", "0.843518", "0.172917", "0.205556"),
            new Box("6", "0.204427", "0.638426", "0.181771", "0.552778"),
            new Box("3", "0.20625", "0.809722", "0.177083", "0.369444")

    );

    @Test
    @DisplayName("리스트를 레이블로 분류되는지 확인")
    void CheckSeatService_박스분류확인() {
        CheckSeatService checkSeatService = new CheckSeatService(boxList);

        assertEquals(checkSeatService.getChairs().size(), 7, "chairs 개수 불일치");
        assertEquals(checkSeatService.getPeoples().size(), 2, "peoples 개수 불일치");
        assertEquals(checkSeatService.getOthers().size(), 3, "others 개수 불일치");
    }

    @Test
    @DisplayName("모든 경우에 맞게 seatCount가 업데이트되는지 확인")
    void CheckSeatService_seatCount작동확인() {
        // given
        CheckSeatService checkSeatService = new CheckSeatService(boxList);

        // when
        checkSeatService.FindHoggedSeat();

        // then

    }
}