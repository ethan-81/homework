package com.homework.mpay.account.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.homework.mpay.common.constant.TransactionTypeCode;
import com.homework.mpay.common.exception.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AccountTest {
    private Account account;
    private PointWindow pointWindow;
    private PointType pointType;

    @BeforeEach
    void setUp() {
        pointWindow = mock(PointWindow.class);
        pointType = mock(PointType.class);
        account = Account.builder().userId("user123").pointWindow(pointWindow).build();
    }

    @Test
    @DisplayName("포인트 지급이 성공해야 한다.")
    void shouldBeSuccessEarn() {
        int amount = 100;

        // given
        when(pointType.isOutOfRange(amount)).thenReturn(false);
        when(pointType.isExceedMaxHoldingAmount(amount)).thenReturn(false);
        when(pointWindow.getTotalAvailableAmountByPointTypeCode(pointType.getCode())).thenReturn(0);

        // when
        account.earnPoint(amount, pointType);

        // then
        assertNotNull(account.getPointTransaction());
        assertEquals(1, account.getPointTransaction().getPoints().size());
        assertEquals(amount, account.getPointTransaction().getAmount());
    }

    @Test
    @DisplayName("포인트 유형 별 금액 제한에 벗어나면 예외를 던져야 한다.")
    void shouldBeThrowExceptionInvalidAmountForPointType() {
        int amount = 100;

        // given
        when(pointType.isOutOfRange(amount)).thenReturn(true);

        // when & then
        assertThrows(InvalidParameterException.class, () -> account.earnPoint(amount, pointType));
    }

    @Test
    @DisplayName("포인트 지급 취소가 성공해야 한다.")
    void shouldBeSuccessEarnCancel() {
        String pointId = "POINT001";
        Point point = mock(Point.class);

        // given
        when(pointWindow.findPointByPointId(pointId)).thenReturn(Optional.of(point));
        when(point.isNotCancelable()).thenReturn(false);
        when(point.earnCancel()).thenReturn(point);

        // when
        account.earnCancelPoint(pointId);

        // then
        assertNotNull(account.getPointTransaction());
        assertEquals(1, account.getPointTransaction().getPoints().size());
    }

    @Test
    @DisplayName("사용 가능한 포인트 목록에 적립 취소 대상 포인트가 존재하지 않으면 취소는 실패해야 한다.")
    void shouldBeFailWhenNotExistCancelTargetPoint() {
        String pointId = "POINT001";

        // given
        when(pointWindow.findPointByPointId(pointId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(InvalidParameterException.class, () -> account.earnCancelPoint(pointId));
    }

    @Test
    @DisplayName("적립 취소 대상 포인트가 일부 사용 되었다면 취소는 실패해야 한다.")
    void shouldBeFailWhenTargetPointUsed() {
        String pointId = "POINT001";
        Point point = mock(Point.class);

        // given
        when(pointWindow.findPointByPointId(pointId)).thenReturn(Optional.of(point));
        when(point.isNotCancelable()).thenReturn(true);

        // when & then
        assertThrows(InvalidParameterException.class, () -> account.earnCancelPoint(pointId));
    }

    @Test
    @DisplayName("보유 포인트의 사용 가능 금액이 충분하다면 포인트 사용은 성공해야 한다.")
    void shouldBeSuccessWhenValidUseRequest() {
        String orderId = "ORDER001";
        int amount = 150;
        Point point1 = mock(Point.class);
        Point point2 = mock(Point.class);

        // given
        when(pointWindow.getSortedPointsForUse()).thenReturn(List.of(point1, point2));
        when(point1.getAvailableAmount()).thenReturn(100);
        when(point2.getAvailableAmount()).thenReturn(100);

        // when
        account.usePointForOder(amount, orderId);

        // then
        assertNotNull(account.getPointTransaction());
        assertEquals(2, account.getPointTransaction().getPoints().size());
    }

    @Test
    @DisplayName("사용 가능한 포인트 금액이 부족하면 포인트 사용은 실패해야 한다.")
    void shouldBeFailWhenNotEnoughUsablePoints() {
        String orderId = "ORDER001";
        int amount = 150;

        // given
        when(pointWindow.getSortedPointsForUse()).thenReturn(List.of());

        // when & then
        assertThrows(InvalidParameterException.class, () -> account.usePointForOder(amount, orderId));
    }

    @Test
    @DisplayName("포인트 사용 취소가 성공해야 한다.")
    void shouldBeSuccessUseCancelPointByOrder() {
        String orderId = "ORDER001";
        String pointId = "POINT001";
        int amount = 100;
        PointTransactionLedger ledger = mock(PointTransactionLedger.class);

        Account orderAccount =
                Account.builder()
                        .userId("user123")
                        .pointWindow(pointWindow)
                        .usedPointLedgers(List.of(ledger))
                        .build();

        Point point = mock(Point.class);
        PointType alternativePointType = mock(PointType.class);

        // given
        when(ledger.getPointId()).thenReturn(pointId);
        when(pointWindow.findPointByPointId(pointId)).thenReturn(Optional.of(point));
        when(ledger.getCancelableAmount()).thenReturn(amount);
        when(point.isExpired()).thenReturn(false);

        // when
        orderAccount.useCancelPointByOrder(orderId, amount, alternativePointType);

        // then
        assertNotNull(orderAccount.getPointTransaction());
        assertEquals(1, orderAccount.getPointTransaction().getPoints().size());
    }

    @Test
    @DisplayName("포인트 사용 취소 시 만료 된 포인트가 존재하면 만료 된 포인트의 사용 취소 금액만큼 적립해야 한다.")
    void shouldBeSuccessWhenPointExpired() {
        int amount = 100;
        String orderId = "ORDER001";
        PointType alternativePointType = mock(PointType.class);

        PointTransactionLedger ledger1 = mock(PointTransactionLedger.class);
        PointTransactionLedger ledger2 = mock(PointTransactionLedger.class);
        String pointId1 = "POINT001";
        String pointId2 = "POINT002";
        Point point1 = mock(Point.class);
        Point point2 = mock(Point.class);

        Account orderAccount =
                Account.builder()
                        .userId("user123")
                        .pointWindow(pointWindow)
                        .usedPointLedgers(List.of(ledger1, ledger2))
                        .build();

        // given
        when(ledger1.getPointId()).thenReturn(pointId1);
        when(pointWindow.findPointByPointId(pointId1)).thenReturn(Optional.of(point1));
        when(ledger1.getCancelableAmount()).thenReturn(50);
        when(point1.isExpired()).thenReturn(false);
        when(point1.useCancel(50)).thenReturn(point1);

        when(ledger2.getPointId()).thenReturn(pointId2);
        when(pointWindow.findPointByPointId(pointId2)).thenReturn(Optional.of(point2));
        when(ledger2.getCancelableAmount()).thenReturn(50);
        when(point2.isExpired()).thenReturn(true);

        // when
        orderAccount.useCancelPointByOrder(orderId, amount, alternativePointType);

        // then
        assertNotNull(orderAccount.getPointTransaction());
        assertEquals(2, orderAccount.getPointTransaction().getPoints().size());
        assertEquals(
                1,
                orderAccount.getPointTransaction().getPoints().stream()
                        .filter(point -> TransactionTypeCode.EARN.equals(point.getActionType()))
                        .count());
    }

    @Test
    @DisplayName("취소 가능 금액이 부족하면 사용 취소는 실패해야 한다.")
    void shouldBeFailWhenNotEnoughCancelablePoints() {
        String orderId = "ORDER001";
        String pointId = "POINT001";
        int amount = 150;
        PointTransactionLedger ledger = mock(PointTransactionLedger.class);

        Account orderAccount =
                Account.builder()
                        .userId("user123")
                        .pointWindow(pointWindow)
                        .usedPointLedgers(List.of(ledger))
                        .build();

        Point point = mock(Point.class);
        PointType alternativePointType = mock(PointType.class);

        // given
        when(ledger.getPointId()).thenReturn(pointId);
        when(pointWindow.findPointByPointId(pointId)).thenReturn(Optional.of(point));
        when(ledger.getCancelableAmount()).thenReturn(50);

        // when & then
        assertThrows(
                InvalidParameterException.class,
                () -> orderAccount.useCancelPointByOrder(orderId, amount, alternativePointType));
    }
}
