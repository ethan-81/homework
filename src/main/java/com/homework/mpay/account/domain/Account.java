package com.homework.mpay.account.domain;

import com.homework.mpay.common.exception.ErrorConstant;
import com.homework.mpay.common.exception.InvalidParameterException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Account {
    private final String userId;
    private final PointWindow pointWindow;
    @Builder.Default private final List<PointTransactionLedger> usedPointLedgers = new ArrayList<>();
    private PointTransaction pointTransaction;

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

        Point earnedPoint = Point.earn(amount, pointType);
        this.pointTransaction = PointTransaction.createEarnTransaction(amount, earnedPoint);
    }

    public void earnCancelPoint(String pointId) {
        Point targetPoint =
                this.pointWindow
                        .findPointByPointId(pointId)
                        .orElseThrow(
                                () -> new InvalidParameterException(ErrorConstant.NOT_EXIST_CANCELABLE_POINT));

        if (targetPoint.isNotCancelable()) {
            throw new InvalidParameterException(ErrorConstant.IS_NOT_CANCELABLE);
        }

        Point canceledPoint = targetPoint.earnCancel();
        this.pointTransaction = PointTransaction.createEarnCancelTransaction(canceledPoint);
    }

    public void usePointForOder(int amount, String orderId) {
        List<Point> pointsForUse = this.pointWindow.getSortedPointsForUse();
        List<Point> usedPoints = new ArrayList<>();
        int remainingAmount = amount;

        for (Point point : pointsForUse) {
            if (remainingAmount <= 0) {
                break;
            }

            int availableAmount = point.getAvailableAmount();
            int usedAmount = Math.min(availableAmount, remainingAmount);
            remainingAmount = remainingAmount - usedAmount;

            usedPoints.add(point.use(usedAmount));
        }

        if (this.isNotEnoughToTransaction(remainingAmount)) {
            throw new InvalidParameterException(ErrorConstant.NOT_ENOUGH_TRANSACTION);
        }

        this.pointTransaction = PointTransaction.createUseTransaction(amount, orderId, usedPoints);
    }

    public void useCancelPointByOrder(String orderId, int amount, PointType alternativePointType) {
        List<PointTransactionLedger> cancelablePointLedgers = this.getCancelablePointLedgers();
        List<Point> useCanceledPoints = new ArrayList<>();
        int remainingAmount = amount;

        for (PointTransactionLedger ledger : cancelablePointLedgers) {
            Point usedPoint =
                    this.pointWindow
                            .findPointByPointId(ledger.getPointId())
                            .orElseThrow(
                                    () -> new InvalidParameterException(ErrorConstant.NOT_EXIST_CANCELABLE_POINT));

            int cancelAmount = Math.min(ledger.getCancelableAmount(), remainingAmount);

            if (usedPoint.isExpired()) {
                useCanceledPoints.add(
                        Point.earnByAlternative(cancelAmount, alternativePointType, usedPoint.getPointId()));
            } else {
                useCanceledPoints.add(usedPoint.useCancel(cancelAmount));
            }

            remainingAmount = remainingAmount - cancelAmount;
        }

        if (this.isNotEnoughToTransaction(remainingAmount)) {
            throw new InvalidParameterException(ErrorConstant.NOT_ENOUGH_TRANSACTION);
        }

        this.pointTransaction =
                PointTransaction.createUseCancelTransaction(amount, orderId, useCanceledPoints);
    }

    private boolean isNotEnoughToTransaction(int remainingAmount) {
        return remainingAmount > 0;
    }

    private List<PointTransactionLedger> getCancelablePointLedgers() {
        return this.usedPointLedgers.stream()
                .filter(ledger -> ledger.getCancelableAmount() > 0)
                .sorted(Comparator.comparingInt(PointTransactionLedger::getCancelableAmount).reversed())
                .toList();
    }
}
