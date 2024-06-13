package com.emptyseat.kss.domain.yolo.dto;

import com.emptyseat.kss.domain.yolo.entity.Box;
import com.emptyseat.kss.domain.yolo.entity.BoxWithoutLabel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class HoggedSeatResponse {
    private List<BoxWithoutLabel> seats;
    private String frame;
}
