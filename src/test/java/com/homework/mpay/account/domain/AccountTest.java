package com.homework.mpay.account.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.homework.mpay.common.exception.ErrorConstant;
import com.homework.mpay.common.exception.InvalidParameterException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AccountTest {
    private Account account;
    private PointWindow pointWindow;
    private PointType pointType;

    @BeforeEach
    void setUp() {
        // `PointWindow`와 `PointType`을 Mock으로 설정
        pointWindow = mock(PointWindow.class);
        pointType = mock(PointType.class);

        // Account 객체 생성
        account = Account.builder().userId("user123").pointWindow(pointWindow).build();
    }

    @Test
    @DisplayName("포인트를 정상적으로 적립할 수 있는 경우")
    void shouldEarnPointSuccessfully() {
        // Given
        int amount = 100;
        int currentTotalAmount = 10;
        String pointTypeCode = "POINT_TYPE_CODE";

        when(pointWindow.getTotalAvailableAmountByPointTypeCode(pointTypeCode))
                .thenReturn(currentTotalAmount);
        when(pointType.getCode()).thenReturn(pointTypeCode);
        when(pointType.isOutOfRange(amount)).thenReturn(false);
        when(pointType.isExceedMaxHoldingAmount(amount)).thenReturn(false);
        when(pointType.calculateExpireDate()).thenReturn(LocalDate.now());

        // When
        account.earnPoint(amount, pointType);

        // Then
        verify(pointWindow).addPoint(any(Point.class));
    }

    @Test
    @DisplayName("최대 보유 한도를 초과하여 포인트 적립 시 예외 발생")
    void shouldThrowExceptionWhenExceedingMaxHoldingAmount() {
        // Given
        String pointTypeCode = "POINT_TYPE_CODE";
        int amount = 100;
        int currentTotalAmount = 900;
        int expectedTotalAmount = currentTotalAmount + amount;

        when(pointWindow.getTotalAvailableAmountByPointTypeCode(pointTypeCode))
                .thenReturn(currentTotalAmount);
        when(pointType.getCode()).thenReturn(pointTypeCode);
        when(pointType.isOutOfRange(amount)).thenReturn(false);
        when(pointType.isExceedMaxHoldingAmount(expectedTotalAmount)).thenReturn(true);

        // When & Then
        InvalidParameterException exception =
                assertThrows(InvalidParameterException.class, () -> account.earnPoint(amount, pointType));

        assertEquals(
                ErrorConstant.EXCEED_HOLDING_POINT_AMOUNT.getErrorMessage(), exception.getErrorMessage());
    }

    @Test
    @DisplayName("적립 포인트 유형의 유효 범위 밖이면 예외 발생")
    void shouldThrowExceptionWhenInvalidEarnAmount() {
        // Given
        String pointTypeCode = "POINT_TYPE_CODE";
        int amount = 100;
        int currentTotalAmount = 900;
        int expectedTotalAmount = currentTotalAmount + amount;

        when(pointWindow.getTotalAvailableAmountByPointTypeCode(pointTypeCode))
                .thenReturn(currentTotalAmount);
        when(pointType.getCode()).thenReturn(pointTypeCode);
        when(pointType.isOutOfRange(amount)).thenReturn(true);
        when(pointType.isExceedMaxHoldingAmount(expectedTotalAmount)).thenReturn(false);

        // When & Then
        InvalidParameterException exception =
                assertThrows(InvalidParameterException.class, () -> account.earnPoint(amount, pointType));

        assertEquals(
                ErrorConstant.INVALID_EARN_POINT_AMOUNT.getErrorMessage(), exception.getErrorMessage());
    }
}
