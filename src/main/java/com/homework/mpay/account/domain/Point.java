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
    private final PointStatusCode status;
    private final LocalDateTime earnedAt;
    private final LocalDateTime modifiedAt;
    private final long pointTypeId;
    private final String pointTypeCode;
    private final String pointTypeName;
    private final int usePriority;
    private final String originPointId;
    private final int transactionAmount;
    private final TransactionTypeCode actionType;

    public static Point earn(int amount, PointType pointType) {
        return Point.builder()
                .earnedAmount(amount)
                .availableAmount(amount)
                .expiredAmount(0)
                .expireDate(pointType.calculateExpireDate())
                .status(PointStatusCode.EARNED)
                .pointTypeId(pointType.getPointTypeId())
                .pointTypeCode(pointType.getCode())
                .pointTypeName(pointType.getName())
                .usePriority(pointType.getUsePriority())
                .transactionAmount(amount)
                .actionType(TransactionTypeCode.EARN)
                .build();
    }

    public boolean isNotCancelable() {
        return this.earnedAmount != this.availableAmount;
    }

    public Point earnCancel() {
        return this.toBuilder()
                .availableAmount(0)
                .status(PointStatusCode.CANCELED)
                .transactionAmount(-this.earnedAmount)
                .actionType(TransactionTypeCode.EARN_CANCEL)
                .build();
    }

    public Point use(int usedAmount) {
        int newAvailableAmount = this.availableAmount - usedAmount;
        PointStatusCode statusCode =
                newAvailableAmount == 0 ? PointStatusCode.COMPLETED : PointStatusCode.EARNED;

        return this.toBuilder()
                .availableAmount(newAvailableAmount)
                .status(statusCode)
                .transactionAmount(-usedAmount)
                .actionType(TransactionTypeCode.USE)
                .build();
    }

    public Point useCancel(int useCanceledAmount) {
        return this.toBuilder()
                .availableAmount(this.availableAmount + useCanceledAmount)
                .status(PointStatusCode.EARNED)
                .transactionAmount(useCanceledAmount)
                .actionType(TransactionTypeCode.USE_CANCEL)
                .build();
    }

    public static Point earnByAlternative(int amount, PointType pointType, String originPointId) {
        return Point.builder()
                .earnedAmount(amount)
                .availableAmount(amount)
                .expiredAmount(0)
                .expireDate(pointType.calculateExpireDate())
                .status(PointStatusCode.EARNED)
                .pointTypeId(pointType.getPointTypeId())
                .pointTypeCode(pointType.getCode())
                .pointTypeName(pointType.getName())
                .usePriority(pointType.getUsePriority())
                .originPointId(originPointId)
                .transactionAmount(amount)
                .actionType(TransactionTypeCode.EARN)
                .build();
    }

    public boolean isExpired() {
        return this.status.equals(PointStatusCode.EXPIRED);
    }
}
