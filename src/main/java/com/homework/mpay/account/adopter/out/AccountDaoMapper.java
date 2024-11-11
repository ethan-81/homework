package com.homework.mpay.account.adopter.out;

import com.homework.mpay.account.adopter.out.entity.PointEntity;
import com.homework.mpay.account.adopter.out.entity.PointProjection;
import com.homework.mpay.account.domain.Account;
import com.homework.mpay.account.domain.Point;
import com.homework.mpay.account.domain.PointWindow;
import com.homework.mpay.common.constant.PointStatusCode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AccountDaoMapper {
    public Account mapToDomain(String userId, List<PointProjection> pointEntities) {
        return Account.builder().userId(userId).pointWindow(mapToPointWindow(pointEntities)).build();
    }

    public PointWindow mapToPointWindow(List<PointProjection> pointEntities) {
        List<Point> points =
                pointEntities.stream()
                        .map(this::mapToPoint)
                        .collect(Collectors.toCollection(ArrayList::new));

        return PointWindow.builder().points(points).build();
    }

    public Point mapToPoint(PointProjection entity) {
        return Point.builder()
                .pointId(entity.getPointId())
                .earnedAmount(entity.getEarnedAmount())
                .availableAmount(entity.getAvailableAmount())
                .expiredAmount(entity.getExpiredAmount())
                .expireDate(entity.getExpireDate())
                .status(PointStatusCode.findByCode(entity.getStatus()))
                .earnedAt(entity.getEarnedAt())
                .modifiedAt(entity.getModifiedAt())
                .pointTypeId(entity.getPointTypeId())
                .pointTypeCode(entity.getPointTypeCode())
                .pointTypeName(entity.getPointTypeName())
                .usePriority(entity.getUsePriority())
                .build();
    }

    public PointEntity mapToPointEntity(Point point, String userId) {
        return PointEntity.builder()
                .pointId(point.getPointId() == null ? null : point.getPointId())
                .userId(userId)
                .pointTypeId(point.getPointTypeId())
                .earnedAmount(point.getEarnedAmount())
                .availableAmount(point.getAvailableAmount())
                .expiredAmount(point.getExpiredAmount())
                .expireDate(point.getExpireDate())
                .status(point.getStatus().getCode())
                .earnedAt(point.getEarnedAt() == null ? null : point.getEarnedAt())
                .build();
    }
}
