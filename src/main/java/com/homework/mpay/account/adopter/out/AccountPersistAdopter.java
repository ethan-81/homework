package com.homework.mpay.account.adopter.out;

import com.homework.mpay.account.adopter.out.entity.PointEntity;
import com.homework.mpay.account.adopter.out.entity.PointProjection;
import com.homework.mpay.account.adopter.out.entity.PointTransactionEntity;
import com.homework.mpay.account.adopter.out.entity.PointTransactionLedgerEntity;
import com.homework.mpay.account.adopter.out.entity.PointTransactionProjection;
import com.homework.mpay.account.adopter.out.entity.PointUseLedgerEntity;
import com.homework.mpay.account.adopter.out.repository.PointRepository;
import com.homework.mpay.account.adopter.out.repository.PointTransactionLedgerRepository;
import com.homework.mpay.account.adopter.out.repository.PointTransactionRepository;
import com.homework.mpay.account.adopter.out.repository.PointUseLedgerRepository;
import com.homework.mpay.account.application.port.out.LoadAccountPort;
import com.homework.mpay.account.application.port.out.UpdateAccountPort;
import com.homework.mpay.account.domain.Account;
import com.homework.mpay.account.domain.Point;
import com.homework.mpay.account.domain.PointTransaction;
import com.homework.mpay.account.domain.PointTransactionLedger;
import com.homework.mpay.account.domain.PointWindow;
import com.homework.mpay.common.constant.PointStatusCode;
import com.homework.mpay.common.constant.TransactionTypeCode;
import com.homework.mpay.common.exception.ErrorConstant;
import com.homework.mpay.common.exception.InvalidParameterException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class AccountPersistAdopter implements LoadAccountPort, UpdateAccountPort {
    private final PointRepository pointRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final PointTransactionLedgerRepository pointTransactionLedgerRepository;
    private final PointUseLedgerRepository pointUseLedgerRepository;
    private final AccountDaoMapper accountDaoMapper;

    @Override
    @Transactional(readOnly = true)
    public Account loadAccountByUserId(String userId) {
        return accountDaoMapper.mapToDomain(userId, this.findByActivePointByUserId(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public Account loadAccountByOrderId(String orderId, String userId) {

        List<PointTransactionProjection> transactions =
                pointTransactionRepository.findAllTransactionsByOrderId(
                        orderId, userId, TransactionTypeCode.USE.getCode());

        List<String> pointIds =
                transactions.stream().map(PointTransactionProjection::getPointId).toList();

        List<Point> usedPoints =
                pointRepository.findByOderUsedPoints(userId, pointIds).stream()
                        .map(accountDaoMapper::mapToPoint)
                        .toList();

        return Account.builder()
                .userId(userId)
                .pointWindow(PointWindow.builder().points(usedPoints).build())
                .usedPointLedgers(
                        transactions.stream()
                                .map(
                                        transaction ->
                                                PointTransactionLedger.builder()
                                                        .pointId(transaction.getPointId())
                                                        .usedAmount(transaction.getUsedAmount())
                                                        .cancelableAmount(transaction.getCancelableAmount())
                                                        .build())
                                .toList())
                .build();
    }

    @Override
    @Transactional
    public void applyTransaction(Account account) {
        String userId = account.getUserId();
        PointTransaction transaction = account.getPointTransaction();

        if (transaction == null) {
            throw new InvalidParameterException(ErrorConstant.UNKNOWN_ERROR);
        }

        PointTransactionEntity savedPointTransactionEntity =
                this.savePointTransactionEntity(
                        userId, transaction.getTransactionTypeCode().getCode(), transaction.getAmount());

        transaction
                .getPoints()
                .forEach(
                        point -> {
                            PointEntity savedPointEntity = this.savePointEntity(point, userId);
                            this.savePointTransactionLedgerEntity(
                                    savedPointTransactionEntity.getPointTransactionId(),
                                    savedPointEntity.getPointId(),
                                    point.getOriginPointId(),
                                    point.getTransactionAmount(),
                                    point.getActionType().getCode());
                        });

        if (this.isTransactionRelatedOrder(transaction)) {
            this.savePointUseLedgerEntity(
                    savedPointTransactionEntity.getPointTransactionId(), transaction.getOrderId());
        }
    }

    private List<PointProjection> findByActivePointByUserId(String userId) {
        return pointRepository.findByUserIdAndStatusIs(userId, PointStatusCode.EARNED.getCode());
    }

    private PointTransactionEntity savePointTransactionEntity(
            String userId, String transactionTypeCode, int amount) {
        return pointTransactionRepository.save(
                PointTransactionEntity.builder()
                        .userId(userId)
                        .transactionType(transactionTypeCode)
                        .amount(amount)
                        .build());
    }

    private PointEntity savePointEntity(Point point, String userId) {
        return pointRepository.save(accountDaoMapper.mapToPointEntity(point, userId));
    }

    private PointTransactionLedgerEntity savePointTransactionLedgerEntity(
            String pointTransactionId,
            String pointId,
            String originPointId,
            int transactionAmount,
            String actionTypeCode) {
        return pointTransactionLedgerRepository.save(
                PointTransactionLedgerEntity.builder()
                        .pointTransactionId(pointTransactionId)
                        .pointId(pointId)
                        .originPointId(StringUtils.hasText(originPointId) ? originPointId : pointId)
                        .transactionAmount(transactionAmount)
                        .actionTypeCode(actionTypeCode)
                        .build());
    }

    private PointUseLedgerEntity savePointUseLedgerEntity(String pointTransactionId, String orderId) {
        return pointUseLedgerRepository.save(
                PointUseLedgerEntity.builder()
                        .pointTransactionId(pointTransactionId)
                        .orderId(orderId)
                        .build());
    }

    private boolean isTransactionRelatedOrder(PointTransaction pointTransaction) {
        return StringUtils.hasText(pointTransaction.getOrderId());
    }
}
