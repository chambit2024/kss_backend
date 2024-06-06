package com.emptyseat.kss.domain.yolo.entity;

import lombok.Getter;


/**
 * value 값들은 추후 재정의 예정
 */

@Getter
public enum LabelType {
    BAG(0),
    BOOK(1),
    BOTTLE(2),
    CHAIR(3),
    LAPTOP(4),
    PAD(5),
    PEOPLE(6),
    PHONE(7);

    private final int value;

    LabelType(int value) {
        this.value = value;
    }

    public static LabelType fromValue(int value) {
        for (LabelType type: values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("labelType이 유효한 값이 아닙니다: " + value);
    }

}
