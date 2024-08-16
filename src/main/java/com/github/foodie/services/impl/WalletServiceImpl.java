package com.github.foodie.services.impl;

import com.github.foodie.constants.TransactionMethodType;
import com.github.foodie.constants.TransactionType;
import com.github.foodie.entities.OrderEntity;
import com.github.foodie.entities.UserAccountEntity;
import com.github.foodie.entities.WalletEntity;
import com.github.foodie.exceptions.Errors;
import com.github.foodie.exceptions.ServiceException;
import com.github.foodie.repositories.WalletRepository;
import com.github.foodie.services.WalletService;
import com.github.foodie.services.WalletTransactionService;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletTransactionService walletTransactionService;

    @Override
    @Transactional
    public WalletEntity addMoneyToWallet(UserAccountEntity userAccountEntity, BigDecimal amount,
                                         String transactionId, OrderEntity orderEntity,
                                         TransactionMethodType transactionMethod) {
        LogstashMarker markers = append("method", "addMoneyToWallet");
        log.info(markers, "adding money to wallet");

        WalletEntity walletEntity = getByUser(userAccountEntity);
        walletEntity.setAmount(walletEntity.getAmount().add(amount));
        walletTransactionService.createWalletTransaction(walletEntity, transactionId, amount,
                orderEntity, transactionMethod, TransactionType.CREDIT);
        walletRepository.save(walletEntity);
        return walletEntity;
    }

    @Override
    public WalletEntity deductMoneyFromWallet(UserAccountEntity userAccountEntity, BigDecimal amount,
                                              String transactionId, OrderEntity orderEntity,
                                              TransactionMethodType transactionMethod) {
        LogstashMarker markers = append("method", "deductMoneyFromWallet");
        log.info(markers, "deducting money from wallet");

        WalletEntity walletEntity = getByUser(userAccountEntity);
        walletEntity.setAmount(walletEntity.getAmount().subtract(amount));
        walletTransactionService.createWalletTransaction(walletEntity, transactionId, amount,
                orderEntity, transactionMethod, TransactionType.DEBIT);
        walletRepository.save(walletEntity);
        return walletEntity;
    }

    @Override
    public void withdrawAllMoneyFromWallet() {
        LogstashMarker markers = append("method", "withdrawAllMoneyFromWallet");
        log.info(markers, "withdrawing all money for wallet");
        //todo get user id from token and check if user is shipper, only then they can withdraw money

    }

    @Override
    public WalletEntity findWalletById(UUID walletId) {
        LogstashMarker markers = append("method", "findWalletById");
        log.info(markers, "finding wallet by its id: {}", walletId);
        WalletEntity entity = walletRepository.findById(walletId).orElse(null);
        if(Objects.isNull(entity)) {
            log.info(markers, "no wallet found for wallet id: {}", walletId);
            throw ServiceException.notFound(Errors.WALLET_NOT_FOUND,
                    Errors.errorMap.get(Errors.WALLET_NOT_FOUND));
        }
        return entity;
    }

    @Override
    public WalletEntity createNewWallet(UserAccountEntity userAccountEntity) {
        LogstashMarker markers = append("method", "createNewWallet");
        log.info(markers, "creating new wallet for user id: {}", userAccountEntity.getId());
        WalletEntity entity = new WalletEntity();
        entity.setUserAccountEntity(userAccountEntity);
        walletRepository.save(entity);
        return entity;
    }

    @Override
    public WalletEntity getByUser(UserAccountEntity userAccountEntity) {
        LogstashMarker markers = append("method", "getByUser");
        log.info(markers, "getting wallet by user with id: {}", userAccountEntity.getId());
        WalletEntity entity = walletRepository.findByUserId(userAccountEntity.getId());
        if(Objects.isNull(entity)) {
            log.info(markers, "no wallet found for user id: {}", userAccountEntity.getId());
            throw ServiceException.notFound(Errors.WALLET_NOT_FOUND,
                    Errors.errorMap.get(Errors.WALLET_NOT_FOUND));
        }
        return entity;
    }
}
