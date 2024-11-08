package com.homework.mpay.account.domain;

import com.homework.mpay.common.constant.PointStatusCode;
import com.homework.mpay.common.constant.TransactionTypeCode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Point {
    private final String pointId;
    private final int earnedAmount;
    private final int availableAmount;
    private final int expiredAmount;
    private final LocalDate expireDate;
    private final long pointTypeId;
    private final String pointTypeCode;
    private final String pointTypeName;
    private final TransactionTypeCode transactionTypeCode;
    private final PointStatusCode status;
    private final LocalDateTime earnedAt;
    private final LocalDateTime modifiedAt;

    public static Point createNewPoint(int amount, PointType pointType) {
        return Point.builder()
                .earnedAmount(amount)
                .availableAmount(amount)
                .expiredAmount(0)
                .expireDate(pointType.calculateExpireDate())
                .pointTypeId(pointType.getPointTypeId())
                .pointTypeCode(pointType.getCode())
                .pointTypeName(pointType.getName())
                .status(PointStatusCode.EARNED)
                .transactionTypeCode(TransactionTypeCode.EARN)
                .build();
    }

    public boolean isNotCancelable() {
        return this.earnedAmount != this.availableAmount;
    }

    public Point cancelEarn() {
        return this.toBuilder()
                .availableAmount(0)
                .status(PointStatusCode.CANCELED)
                .transactionTypeCode(TransactionTypeCode.EARN_CANCEL)
                .build();
    }
}
