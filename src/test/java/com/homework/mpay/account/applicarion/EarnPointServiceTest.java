package com.homework.mpay.account.applicarion;

import com.homework.mpay.account.application.port.out.LoadAccountPort;
import com.homework.mpay.account.application.port.out.LoadPointTypePort;
import com.homework.mpay.account.application.port.out.UpdateAccountPort;
import com.homework.mpay.account.application.service.EarnPointService;
import com.homework.mpay.account.domain.Account;
import com.homework.mpay.account.domain.PointType;
import com.homework.mpay.common.constant.PointTypeCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EarnPointServiceTest {
    @Mock
    private LoadAccountPort loadAccountPort;
    @Mock
    private UpdateAccountPort updateAccountPort;
    @Mock
    private LoadPointTypePort loadPointTypePort;
    @InjectMocks
    private EarnPointService earnPointService;

    private final String userId = "testUser";
    private final int amount = 100;
    private final PointTypeCode pointTypeCode = PointTypeCode.FREE_POINT;

    private Account mockAccount;
    private PointType mockPointType;

    @BeforeEach
    void setUp() {
        mockPointType = mock(PointType.class);
        mockAccount = mock(Account.class);

        when(loadPointTypePort.loadPointTypeByCode(pointTypeCode.getCode())).thenReturn(mockPointType);
        when(loadAccountPort.loadAccountByUserId(userId)).thenReturn(mockAccount);
    }

    @Test
    void shouldAddPointsToAccountWhenValidRequest() {
        // When - earnPoint 메서드가 호출될 때
        assertDoesNotThrow(() -> earnPointService.earnPoint(userId, amount, pointTypeCode));

        // Then - 계정에 포인트가 추가되고 업데이트 포트가 호출됨
        verify(mockAccount, times(1)).earnPoint(amount, mockPointType);
        verify(updateAccountPort, times(1)).addNewPoints(mockAccount);
    }
}
