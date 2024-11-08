package com.homework.mpay.account.application.port.out;

import com.homework.mpay.account.domain.PointType;

public interface LoadPointTypePort {
    PointType loadPointTypeByCode(String code);
}
