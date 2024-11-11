package com.homework.mpay.account.application.port.out;

import com.homework.mpay.account.domain.Account;

public interface LoadAccountPort {
    Account loadAccountByUserId(String userId);

    Account loadAccountByOrderId(String orderId, String userId);
}
