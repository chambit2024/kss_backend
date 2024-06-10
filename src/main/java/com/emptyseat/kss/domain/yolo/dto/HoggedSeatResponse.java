package com.emptyseat.kss.domain.yolo.dto;

import com.emptyseat.kss.domain.yolo.entity.Box;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class HoggedSeatResponse {
    private List<Box> seats;
    private String frame;
}
