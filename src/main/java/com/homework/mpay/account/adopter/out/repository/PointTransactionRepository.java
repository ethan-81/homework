package com.homework.mpay.account.adopter.out.repository;

import com.homework.mpay.account.adopter.out.entity.PointTransactionEntity;
import com.homework.mpay.account.adopter.out.entity.PointTransactionProjection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointTransactionRepository extends JpaRepository<PointTransactionEntity, String> {
    @Query(
            value =
                    "select "
                            + "l.originPointId as pointId, "
                            + "abs(sum(case when t.transactionType = :useTransactionTypeCode then l.transactionAmount else 0 end)) as usedAmount, "
                            + "abs(sum(l.transactionAmount)) as cancelableAmount "
                            + "from PointTransactionEntity t "
                            + "inner join PointUseLedgerEntity u "
                            + "on t.pointTransactionId = u.pointTransactionId "
                            + "and u.orderId = :orderId "
                            + "inner join PointTransactionLedgerEntity l on t.pointTransactionId = l.pointTransactionId "
                            + "where t.userId = :userId "
                            + "group by l.originPointId ")
    List<PointTransactionProjection> findAllTransactionsByOrderId(
            @Param("orderId") String orderId,
            @Param("userId") String userId,
            @Param("useTransactionTypeCode") String useTransactionTypeCode);
}
