package com.homework.mpay.account.adopter.out.entity;

public interface PointTransactionProjection {
    String getPointId();

    int getUsedAmount();

    int getCancelableAmount();
}
