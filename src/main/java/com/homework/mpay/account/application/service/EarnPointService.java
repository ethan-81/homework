package com.homework.mpay.account.application.service;

import com.homework.mpay.account.application.port.in.EarnPointUseCase;
import com.homework.mpay.account.application.port.out.LoadAccountPort;
import com.homework.mpay.account.application.port.out.LoadPointTypePort;
import com.homework.mpay.account.application.port.out.UpdateAccountPort;
import com.homework.mpay.account.domain.Account;
import com.homework.mpay.account.domain.PointType;
import com.homework.mpay.common.constant.PointTypeCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EarnPointService implements EarnPointUseCase {
    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountPort updateAccountPort;
    private final LoadPointTypePort loadPointTypePort;

    @Override
    public void earnPoint(String userId, int amount, PointTypeCode pointTypeCode) {
        PointType pointType = loadPointTypePort.loadPointTypeByCode(pointTypeCode.getCode());

        Account account = loadAccountPort.loadAccountByUserId(userId);
        account.earnPoint(amount, pointType);

        updateAccountPort.addNewPoints(account);
    }
}
