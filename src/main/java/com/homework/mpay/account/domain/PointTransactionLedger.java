package com.homework.mpay.account.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class PointTransactionLedger {
    private final String pointId;
    private final int usedAmount;
    private final int cancelableAmount;
}
