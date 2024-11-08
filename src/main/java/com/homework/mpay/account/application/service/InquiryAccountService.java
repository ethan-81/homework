package com.homework.mpay.account.application.service;

import com.homework.mpay.account.application.port.in.InquiryAccountUseCase;
import com.homework.mpay.account.application.port.out.LoadAccountPort;
import com.homework.mpay.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryAccountService implements InquiryAccountUseCase {
    private final LoadAccountPort loadAccountPort;

    @Override
    public Account inquiryAccount(String userId) {
        return loadAccountPort.loadAccountByUserId(userId);
    }
}
