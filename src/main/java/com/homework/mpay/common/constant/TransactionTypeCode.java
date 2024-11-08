package com.homework.mpay.common.constant;

import lombok.Getter;

@Getter
public enum TransactionTypeCode {
    EARN("EARN", "적립"),
    EARN_CANCEL("EARN_CANCEL", "적립 취소"),
    USE("USE", "사용"),
    USE_CANCEL("USE_CANCEL", "사용 취소"),
    NONE("NONE", "");

    private final String code;
    private final String description;

    TransactionTypeCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
