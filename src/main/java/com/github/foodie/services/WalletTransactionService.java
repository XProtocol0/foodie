package com.github.foodie.services;

import com.github.foodie.constants.TransactionMethodType;
import com.github.foodie.constants.TransactionType;
import com.github.foodie.entities.OrderEntity;
import com.github.foodie.entities.WalletEntity;
import com.github.foodie.entities.WalletTransactionEntity;

import java.math.BigDecimal;

public interface WalletTransactionService {
    WalletTransactionEntity createWalletTransaction(WalletEntity walletEntity, String transactionId, BigDecimal amount,
                                                       OrderEntity orderEntity, TransactionMethodType transactionMethod,
                                                       TransactionType transactionType);
}
