package com.homework.mpay.account.application.port.out;

import com.homework.mpay.account.domain.Account;

public interface UpdateAccountPort {
    void updatePoints(Account account);
}
