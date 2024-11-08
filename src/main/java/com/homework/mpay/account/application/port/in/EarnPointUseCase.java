package com.homework.mpay.account.application.port.in;

import com.homework.mpay.common.constant.PointTypeCode;

public interface EarnPointUseCase {
    void earnPoint(String userId, int amount, PointTypeCode pointTypeCode);

    void cancelEarnPoint(String userId, String pointId);
}
