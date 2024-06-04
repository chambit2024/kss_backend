package com.emptyseat.kss.domain.yolo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Box {
    private LabelType labelType;
    private double topleft_x;
    private double topleft_y;
    private double width;
    private double height;

    public Box(String labelType, String x, String y, String w, String h) {
        int labelTypeValue = Integer.parseInt(labelType);
        this.labelType = LabelType.fromValue(labelTypeValue);
        this.topleft_x = Double.parseDouble(x);
        this.topleft_y = Double.parseDouble(y);
        this.width = Double.parseDouble(w);
        this.height = Double.parseDouble(h);
    }

    @Override
    public String toString() {
        return "Box{" +
                "labelType=" + labelType +
                ", topleft_x=" + topleft_x +
                ", topleft_y=" + topleft_y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
