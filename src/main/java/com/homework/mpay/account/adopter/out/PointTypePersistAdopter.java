package com.homework.mpay.account.adopter.out;

import com.homework.mpay.account.adopter.out.entity.PointTypeEntity;
import com.homework.mpay.account.adopter.out.repository.PointTypeRepository;
import com.homework.mpay.account.application.port.out.LoadPointTypePort;
import com.homework.mpay.account.domain.PointType;
import com.homework.mpay.common.exception.ErrorConstant;
import com.homework.mpay.common.exception.InvalidParameterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PointTypePersistAdopter implements LoadPointTypePort {
    private final PointTypeRepository pointTypeRepository;
    private final PointTypeDaoMapper pointTypeDaoMapper;

    @Override
    @Transactional(readOnly = true)
    public PointType loadPointTypeByCode(String code) {
        PointTypeEntity pointTypeEntity =
                pointTypeRepository
                        .findByCodeAndActiveIsTrue(code)
                        .orElseThrow(
                                () -> new InvalidParameterException(ErrorConstant.NOT_EXIST_POINT_TYPE_DATA));
        return pointTypeDaoMapper.mapToDomain(pointTypeEntity);
    }
}
