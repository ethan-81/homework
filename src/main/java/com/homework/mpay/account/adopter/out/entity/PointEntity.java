package com.homework.mpay.account.adopter.out.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Builder
@Table(name = "point")
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final String pointId;

    @Column(nullable = false)
    private final String userId;

    @Column(nullable = false)
    private final long pointTypeId;

    @Column(nullable = false)
    private final String pointTypeCode;

    @Column(nullable = false)
    private final String pointTypeName;

    @Column(nullable = false)
    private final int earnedAmount;

    @Column(nullable = false)
    private final int availableAmount;

    @Column(nullable = false)
    private final int expiredAmount;

    @Column(nullable = false)
    private final LocalDate expireDate;

    @Column(nullable = false)
    private final String status;

    @CreationTimestamp
    @Column(nullable = false)
    private final LocalDateTime earnedAt;

    @LastModifiedDate @Column private LocalDateTime modifiedAt;
}
