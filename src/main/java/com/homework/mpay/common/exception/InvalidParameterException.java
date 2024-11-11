package com.homework.mpay.common.exception;

public class InvalidParameterException extends BaseException {
    public InvalidParameterException(ErrorConstant errorConstant) {
        super(errorConstant.getErrorCode(), errorConstant.getErrorMessage());
    }
}
