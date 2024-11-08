package com.homework.mpay.account.adopter.out.repository;

import com.homework.mpay.account.adopter.out.entity.PointTypeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTypeRepository extends JpaRepository<PointTypeEntity, Long> {
    Optional<PointTypeEntity> findByCodeAndActiveIsTrue(String code);
}
