package com.homework.mpay.account.adopter.out.repository;

import com.homework.mpay.account.adopter.out.entity.PointTransactionLedgerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionLedgerRepository
        extends JpaRepository<PointTransactionLedgerEntity, Long> {}
