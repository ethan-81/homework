package com.homework.mpay.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorConstant {
    UNKNOWN_ERROR("1", "알수 없는 오류 발생."),
    INVALID_REQUEST_DATA("2", "유효하지 않는 요청 데이터 입니다."),
    NOT_EXIST_POINT_TYPE_DATA("3", "포인트 타입 정보가 존재하지 않습니다."),
    INVALID_EARN_POINT_AMOUNT("4", "지급이 불가능한 금액입니다."),
    EXCEED_HOLDING_POINT_AMOUNT("5", "최대 보유 한도를 초과했습니다."),
    ;

    private final String errorCode;
    private final String errorMessage;
}
