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
@Table(name = "point_ledger")
public class PointLedgerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointLedgerId;

    @Column(nullable = false)
    private final String pointId;

    @Column(nullable = false)
    private final String pointTransactionId;
}
