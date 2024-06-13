package com.emptyseat.kss.domain.yolo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoxWithoutLabel {
    private double x;
    private double y;
    private double w;
    private double h;

    public BoxWithoutLabel(String x, String y, String w, String h) {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.w = Double.parseDouble(w);
        this.h = Double.parseDouble(h);
    }

    public double boxSize() {
        return this.w * this.h;
    }

    public boolean isBigger(BoxWithoutLabel box) {
        return this.boxSize() > box.boxSize();
    }

    @Override
    public String toString() {
        return "Box{" +
                "center_x=" + x +
                ", center_y=" + y +
                ", width=" + w +
                ", height=" + h +
                '}';
    }
}
