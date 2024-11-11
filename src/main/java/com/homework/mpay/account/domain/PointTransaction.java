package com.homework.mpay.account.domain;

import com.homework.mpay.common.constant.TransactionTypeCode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class PointTransaction {
    private final String pointTransactionId;
    private final TransactionTypeCode transactionTypeCode;
    private final int amount;
    private final String orderId;
    private final LocalDateTime transactionAt;
    @Builder.Default private final List<Point> points = new ArrayList<>();

    public static PointTransaction createEarnTransaction(int amount, Point earnedPoint) {
        return PointTransaction.builder()
                .transactionTypeCode(TransactionTypeCode.EARN)
                .amount(amount)
                .points(List.of(earnedPoint))
                .build();
    }

    public static PointTransaction createEarnCancelTransaction(Point canceledPoint) {
        return PointTransaction.builder()
                .transactionTypeCode(TransactionTypeCode.EARN_CANCEL)
                .amount(canceledPoint.getEarnedAmount())
                .points(List.of(canceledPoint))
                .build();
    }

    public static PointTransaction createUseTransaction(
            int amount, String orderId, List<Point> usedPoints) {
        return PointTransaction.builder()
                .transactionTypeCode(TransactionTypeCode.USE)
                .amount(amount)
                .orderId(orderId)
                .points(usedPoints)
                .build();
    }

    public static PointTransaction createUseCancelTransaction(
            int amount, String orderId, List<Point> useCanceledPoints) {
        return PointTransaction.builder()
                .transactionTypeCode(TransactionTypeCode.USE_CANCEL)
                .amount(amount)
                .orderId(orderId)
                .points(useCanceledPoints)
                .build();
    }
}
