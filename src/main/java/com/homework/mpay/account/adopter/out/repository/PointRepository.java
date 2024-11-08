package com.homework.mpay.account.adopter.out.repository;

import com.homework.mpay.account.adopter.out.entity.PointEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, String> {
    List<PointEntity> findByUserId(String userId);
}
