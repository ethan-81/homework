package com.homework.mpay.account.domain;

import com.homework.mpay.common.constant.TransactionTypeCode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
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
                .transactionTypeCode(TransactionTypeCode.EARN)
                .build();
    }
}
