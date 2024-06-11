package com.emptyseat.kss.domain.yolo.dto;

import com.emptyseat.kss.domain.yolo.entity.Box;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class HoggedSeatResponse {
    private List<Box> seats;
    private String frame;
}
