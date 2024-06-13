package com.emptyseat.kss.domain.yolo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Box {
    private LabelType labelType;
    private double center_x;
    private double center_y;
    private double width;
    private double height;

    public Box(String labelType, String x, String y, String w, String h) {
        int labelTypeValue = Integer.parseInt(labelType);
        this.labelType = LabelType.fromValue(labelTypeValue);
        this.center_x = Double.parseDouble(x);
        this.center_y = Double.parseDouble(y);
        this.width = Double.parseDouble(w);
        this.height = Double.parseDouble(h);
    }

    public double boxSize() {
        return this.width * this.height;
    }

    public boolean isBigger(Box box) {
        return this.boxSize() > box.boxSize();
    }

    @Override
    public String toString() {
        return "Box{" +
                "labelType=" + labelType +
                ", center_x=" + center_x +
                ", center_y=" + center_y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
    public boolean equals(Pos pos) {
        if (pos == null) return false;
        return center_x == pos.getX() && center_y == pos.getY();
    }

    public boolean equals(Box box) {
        if (this == box) return true;
        if (box == null) return false;
        return center_x == box.getCenter_x() && center_y == box.getCenter_y();
    }
}
