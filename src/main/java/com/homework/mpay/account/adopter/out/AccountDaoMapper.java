package com.homework.mpay.account.adopter.out;

import com.homework.mpay.account.adopter.out.entity.PointEntity;
import com.homework.mpay.account.domain.Account;
import com.homework.mpay.account.domain.Point;
import com.homework.mpay.account.domain.PointWindow;
import com.homework.mpay.common.constant.TransactionTypeCode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AccountDaoMapper {
    Account mapToDomain(String userId, List<PointEntity> pointEntities) {
        return Account.builder().userId(userId).pointWindow(mapToPointWindow(pointEntities)).build();
    }

    PointWindow mapToPointWindow(List<PointEntity> pointEntities) {
        List<Point> points =
                pointEntities.stream()
                        .map(this::mapToPoint)
                        .collect(Collectors.toCollection(ArrayList::new));

        return PointWindow.builder().points(points).build();
    }

    Point mapToPoint(PointEntity entity) {
        return Point.builder()
                .pointId(entity.getPointId())
                .earnedAmount(entity.getEarnedAmount())
                .availableAmount(entity.getAvailableAmount())
                .expireDate(entity.getExpireDate())
                .pointTypeId(entity.getPointTypeId())
                .pointTypeCode(entity.getPointTypeCode())
                .pointTypeName(entity.getPointTypeName())
                .transactionTypeCode(TransactionTypeCode.NONE)
                .earnedAt(entity.getEarnedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }

    PointEntity mapToPointEntity(Point point, String userId) {
        return PointEntity.builder()
                .userId(userId)
                .pointTypeId(point.getPointTypeId())
                .pointTypeCode(point.getPointTypeCode())
                .pointTypeName(point.getPointTypeName())
                .earnedAmount(point.getEarnedAmount())
                .availableAmount(point.getAvailableAmount())
                .expiredAmount(point.getExpiredAmount())
                .expireDate(point.getExpireDate())
                .build();
    }
}
