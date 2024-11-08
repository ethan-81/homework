package com.homework.mpay.account.domain;

import com.homework.mpay.common.exception.ErrorConstant;
import com.homework.mpay.common.exception.InvalidParameterException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Account {
    private final String userId;
    private final PointWindow pointWindow;

    public void earnPoint(int amount, PointType pointType) {
        int currentTotalAmount =
                pointWindow.getTotalAvailableAmountByPointTypeCode(pointType.getCode());
        int expectedTotalAmount = currentTotalAmount + amount;

        if (pointType.isOutOfRange(amount)) {
            throw new InvalidParameterException(ErrorConstant.INVALID_EARN_POINT_AMOUNT);
        }

        if (pointType.isExceedMaxHoldingAmount(expectedTotalAmount)) {
            throw new InvalidParameterException(ErrorConstant.EXCEED_HOLDING_POINT_AMOUNT);
        }

        this.pointWindow.addPoint(Point.createNewPoint(amount, pointType));
    }

    public void cancelEarnPoint(String pointId) {
        Point targetPoint =
                this.pointWindow
                        .findPointByPointId(pointId)
                        .orElseThrow(
                                () -> new InvalidParameterException(ErrorConstant.NOT_EXIST_CANCELABLE_POINT));

        if (targetPoint.isNotCancelable()) {
            throw new InvalidParameterException(ErrorConstant.IS_NOT_CANCELABLE);
        }

        Point canceledPoint = targetPoint.cancelEarn();
        this.pointWindow.updatePoint(canceledPoint);
    }
}
