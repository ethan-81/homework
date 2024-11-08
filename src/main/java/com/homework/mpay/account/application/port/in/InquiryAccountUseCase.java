package com.homework.mpay.account.application.port.in;

import com.homework.mpay.account.domain.Account;

public interface InquiryAccountUseCase {
    Account inquiryAccount(String userId);
}
