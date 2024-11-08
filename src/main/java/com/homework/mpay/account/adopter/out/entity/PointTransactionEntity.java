package com.homework.mpay.account.adopter.out.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Builder
@Table(name = "point_transaction")
public class PointTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final String pointTransactionId;

    @Column(nullable = false)
    private final String userId;

    @Column(nullable = false)
    private final String transactionType;

    @Column(nullable = false)
    private final int amount;

    @CreationTimestamp
    @Column(nullable = false)
    private final LocalDateTime transactionAt;
}
