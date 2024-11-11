package com.homework.mpay.account.adopter.out.repository;

import com.homework.mpay.account.adopter.out.entity.PointEntity;
import com.homework.mpay.account.adopter.out.entity.PointProjection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, String> {
    @Query(
            value =
                    "select "
                            + "p.pointId as pointId, "
                            + "p.userId as userId,"
                            + "p.earnedAmount as earnedAmount,"
                            + "p.availableAmount as availableAmount,"
                            + "p.expiredAmount as expiredAmount,"
                            + "p.expireDate as expireDate,"
                            + "p.status as status,"
                            + "p.earnedAt as earnedAt,"
                            + "p.modifiedAt as modifiedAt,"
                            + "p.pointTypeId as pointTypeId,"
                            + "t.code as pointTypeCode, "
                            + "t.name as pointTypeName, "
                            + "t.usePriority as usePriority "
                            + "from PointEntity p "
                            + "inner join PointTypeEntity t on p.pointTypeId = t.pointTypeId "
                            + "where p.userId = :userId "
                            + "and p.status = :status")
    List<PointProjection> findByUserIdAndStatusIs(
            @Param("userId") String userId, @Param("status") String status);

    @Query(
            value =
                    "select "
                            + "p.pointId as pointId, "
                            + "p.userId as userId,"
                            + "p.earnedAmount as earnedAmount,"
                            + "p.availableAmount as availableAmount,"
                            + "p.expiredAmount as expiredAmount,"
                            + "p.expireDate as expireDate,"
                            + "p.status as status,"
                            + "p.earnedAt as earnedAt,"
                            + "p.modifiedAt as modifiedAt,"
                            + "p.pointTypeId as pointTypeId,"
                            + "t.code as pointTypeCode, "
                            + "t.name as pointTypeName, "
                            + "t.usePriority as usePriority "
                            + "from PointEntity p "
                            + "inner join PointTypeEntity t on p.pointTypeId = t.pointTypeId "
                            + "where p.userId = :userId "
                            + "and p.pointId in (:pointIds)")
    List<PointProjection> findByOderUsedPoints(
            @Param("userId") String userId, @Param("pointIds") List<String> pointIds);
}
