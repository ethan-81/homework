package com.homework.mpay.common.constant;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum PointStatusCode {
    EARNED("EARNED", "적립"),
    CANCELED("CANCELED", "취소"),
    COMPLETED("COMPLETED", "사용 완료"),
    EXPIRED("EXPIRED", "만료");

    private final String code;
    private final String description;

    PointStatusCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static PointStatusCode findByCode(String code) {
        return Arrays.stream(PointStatusCode.values())
                .filter(pointStatusCode -> pointStatusCode.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("undefined PointStatusCode!!!"));
    }
}
