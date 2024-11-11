package com.homework.mpay.account.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class PointType {
    private final long pointTypeId;
    private final String code;
    private final String name;
    private final int maxEarnAmount;
    private final int minEarnAmount;
    private final int maxHoldingAmount;
    private final int validDay;
    private final int usePriority;

    public boolean isExceedMaxHoldingAmount(int amount) {
        return this.maxHoldingAmount < amount;
    }

    public boolean isOutOfRange(int amount) {
        return this.minEarnAmount >= amount && this.maxEarnAmount <= amount;
    }

    public LocalDate calculateExpireDate() {
        return LocalDate.now().plusDays(this.validDay);
    }
}
