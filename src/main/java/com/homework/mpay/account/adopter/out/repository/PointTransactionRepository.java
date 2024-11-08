package com.homework.mpay.account.adopter.out.repository;

import com.homework.mpay.account.adopter.out.entity.PointTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionRepository extends JpaRepository<PointTransactionEntity, String> {}
