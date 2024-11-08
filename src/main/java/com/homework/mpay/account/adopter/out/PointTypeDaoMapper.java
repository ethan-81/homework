package com.homework.mpay.account.adopter.out;

import com.homework.mpay.account.adopter.out.entity.PointTypeEntity;
import com.homework.mpay.account.domain.PointType;
import org.springframework.stereotype.Component;

@Component
public class PointTypeDaoMapper {
    PointType mapToDomain(PointTypeEntity pointTypeEntity) {
        return PointType.builder()
                .pointTypeId(pointTypeEntity.getPointTypeId())
                .name(pointTypeEntity.getName())
                .code(pointTypeEntity.getCode())
                .maxEarnAmount(pointTypeEntity.getMaxEarnAmount())
                .minEarnAmount(pointTypeEntity.getMinEarnAmount())
                .maxHoldingAmount(pointTypeEntity.getMaxHoldingAmount())
                .validDay(pointTypeEntity.getValidDay())
                .build();
    }
}
