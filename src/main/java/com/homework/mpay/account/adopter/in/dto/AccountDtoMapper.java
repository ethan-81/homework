package com.homework.mpay.account.adopter.in.dto;

import com.homework.mpay.account.domain.Account;
import com.homework.mpay.account.domain.Point;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoMapper {
    public GetAccountResponse mapToGetAccountResponse(Account account) {
        return GetAccountResponse.builder()
                .userId(account.getUserId())
                .points(
                        account.getPointWindow().getPoints().stream().map(this::mapToPointResponse).toList())
                .build();
    }

    public PointResponse mapToPointResponse(Point point) {
        return PointResponse.builder()
                .pointId(point.getPointId())
                .earnedAmount(point.getEarnedAmount())
                .availableAmount(point.getAvailableAmount())
                .expiredAmount(point.getExpiredAmount())
                .expireDate(point.getExpireDate())
                .pointTypeCode(point.getPointTypeCode())
                .pointTypeName(point.getPointTypeName())
                .earnedAt(point.getEarnedAt())
                .modifiedAt(point.getModifiedAt())
                .build();
    }
}
