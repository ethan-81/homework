package com.homework.mpay.account.adopter.out.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Builder
@Table(name = "point_type")
public class PointTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long pointTypeId;

    @Column(nullable = false)
    private final String code;

    @Column(nullable = false)
    private final String name;

    @Column(nullable = false)
    private final int maxEarnAmount;

    @Column(nullable = false)
    private final int minEarnAmount;

    @Column(nullable = false)
    private final int maxHoldingAmount;

    @Column(nullable = false)
    private final int validDay;

    @Column(nullable = false)
    private final boolean active;
}
