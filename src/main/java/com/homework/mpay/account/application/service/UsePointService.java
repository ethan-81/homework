package com.homework.mpay.account.application.service;

import com.homework.mpay.account.application.port.in.UsePointUseCase;
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
public class UsePointService implements UsePointUseCase {
    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountPort updateAccountPort;
    private final LoadPointTypePort loadPointTypePort;

    @Override
    public void usePoint(String userId, int amount, String orderId) {
        Account account = loadAccountPort.loadAccountByUserId(userId);
        account.usePointForOder(amount, orderId);
        updateAccountPort.applyTransaction(account);
    }

    @Override
    public void useCancelPoint(String userId, int amount, String orderId) {
        PointType alternativePointType =
                loadPointTypePort.loadPointTypeByCode(PointTypeCode.ALTERNATIVE_POINT.getCode());

        Account account = loadAccountPort.loadAccountByOrderId(orderId, userId);
        account.useCancelPointByOrder(orderId, amount, alternativePointType);
        updateAccountPort.applyTransaction(account);
    }
}
