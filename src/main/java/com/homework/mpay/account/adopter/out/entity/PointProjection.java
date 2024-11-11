package com.homework.mpay.account.adopter.out.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PointProjection {
    String getPointId();

    String getUserId();

    int getEarnedAmount();

    int getAvailableAmount();

    int getExpiredAmount();

    LocalDate getExpireDate();

    String getStatus();

    LocalDateTime getEarnedAt();

    LocalDateTime getModifiedAt();

    int getPointTypeId();

    String getPointTypeCode();

    String getPointTypeName();

    int getUsePriority();
}
