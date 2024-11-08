package com.homework.mpay.common.constant;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum PointTypeCode {
    FREE_POINT("FREE_POINT", "무료 포인트"),
    MANUAL_POINT("MANUAL_POINT", "수기 지급 포인트");

    private final String code;
    private final String description;

    PointTypeCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static PointTypeCode findByCode(String code) {
        return Arrays.stream(PointTypeCode.values())
                .filter(pointTypeCode -> pointTypeCode.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("undefined PointTypeCode!!!"));
    }
}
