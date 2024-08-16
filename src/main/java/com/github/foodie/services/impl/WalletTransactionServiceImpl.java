package com.github.foodie.services.impl;

import com.github.foodie.constants.TransactionMethodType;
import com.github.foodie.constants.TransactionType;
import com.github.foodie.entities.OrderEntity;
import com.github.foodie.entities.WalletEntity;
import com.github.foodie.entities.WalletTransactionEntity;
import com.github.foodie.repositories.WalletTransactionRepository;
import com.github.foodie.services.WalletTransactionService;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class WalletTransactionServiceImpl implements WalletTransactionService {

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Override
    @Transactional
    public WalletTransactionEntity createWalletTransaction(WalletEntity walletEntity, String transactionId,
                                           BigDecimal amount, OrderEntity orderEntity,
                                           TransactionMethodType transactionMethod, TransactionType transactionType) {
        LogstashMarker markers = append("method", "createWalletTransaction");
        log.info(markers, "creating wallet transaction");
        WalletTransactionEntity walletTransactionEntity = new WalletTransactionEntity();
        walletTransactionEntity.setWallet(walletEntity);
        walletTransactionEntity.setWalletId(walletEntity.getId());
        walletTransactionEntity.setAmount(amount);
        walletTransactionEntity.setOrderEntity(orderEntity);
        walletTransactionEntity.setOrderId(orderEntity.getId());
        walletTransactionEntity.setTransactionMethodType(transactionMethod);
        walletTransactionEntity.setTransactionType(transactionType);
        walletTransactionRepository.save(walletTransactionEntity);
        return walletTransactionEntity;
    }
}
