package com.homework.mpay.common.exception;

public class UnexpectedException extends BaseException {
    public UnexpectedException(Throwable cause) {
        super(
                ErrorConstant.UNKNOWN_ERROR.getErrorCode(),
                ErrorConstant.UNKNOWN_ERROR.getErrorMessage(),
                cause);
    }
}
