package com.emptyseat.kss.domain.yolo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class YoloFileParseResponse {
    private String label;
    private String pos_x;
    private String pos_y;
    private String width;
    private String height;
}
