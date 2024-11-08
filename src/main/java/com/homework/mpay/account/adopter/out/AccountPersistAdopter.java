package com.homework.mpay.account.adopter.out;

import com.homework.mpay.account.adopter.out.entity.PointEntity;
import com.homework.mpay.account.adopter.out.entity.PointLedgerEntity;
import com.homework.mpay.account.adopter.out.entity.PointTransactionEntity;
import com.homework.mpay.account.adopter.out.repository.PointLedgerRepository;
import com.homework.mpay.account.adopter.out.repository.PointRepository;
import com.homework.mpay.account.adopter.out.repository.PointTransactionRepository;
import com.homework.mpay.account.application.port.out.LoadAccountPort;
import com.homework.mpay.account.application.port.out.UpdateAccountPort;
import com.homework.mpay.account.domain.Account;
import java.util.List;

import com.homework.mpay.account.domain.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class AccountPersistAdopter implements LoadAccountPort, UpdateAccountPort {
    private final PointRepository pointRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final PointLedgerRepository pointLedgerRepository;
    private final AccountDaoMapper accountDaoMapper;

    @Override
    @Transactional(readOnly = true)
    public Account loadAccountByUserId(String userId) {
        List<PointEntity> pointEntities = pointRepository.findByUserId(userId);
        return accountDaoMapper.mapToDomain(userId, pointEntities);
    }

    @Override
    @Transactional
    public void addNewPoints(Account account) {
        account
                .getPointWindow()
                .getNewPoints()
                .forEach(
                        point -> {
                            PointEntity savedPointEntity = savePointEntity(point, account.getUserId());
                            PointTransactionEntity savedPointTransactionEntity = savePointTransactionEntity(account.getUserId(), point.getTransactionTypeCode().getCode(), point.getEarnedAmount());
                            savePointLedgerEntity(savedPointEntity.getPointId(), savedPointTransactionEntity.getPointTransactionId());
                        });
    }

    public PointEntity savePointEntity(Point point, String userId) {
        return pointRepository.save(accountDaoMapper.mapToPointEntity(point, userId));
    }

    public PointTransactionEntity savePointTransactionEntity(String userId, String transactionTypeCode, int amount) {
        return pointTransactionRepository.save(PointTransactionEntity.builder()
                .userId(userId)
                .transactionType(transactionTypeCode)
                .amount(amount)
                .build());
    }

    public PointLedgerEntity savePointLedgerEntity(String pointId, String pointTransactionId) {
        return pointLedgerRepository.save(PointLedgerEntity.builder()
                .pointId(pointId)
                .pointTransactionId(pointTransactionId)
                .build());
    }
}
