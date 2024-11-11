package com.homework.mpay.account.application.port.in;

public interface UsePointUseCase {
    void usePoint(String userId, int amount, String orderId);

    void useCancelPoint(String userId, int amount, String orderId);
}
