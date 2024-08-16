package com.github.foodie.services;

import com.github.foodie.constants.TransactionMethodType;
import com.github.foodie.entities.OrderEntity;
import com.github.foodie.entities.UserAccountEntity;
import com.github.foodie.entities.WalletEntity;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletService {

    WalletEntity addMoneyToWallet(UserAccountEntity userAccountEntity, BigDecimal amount,
                                  String transactionId, OrderEntity orderEntity,
                                  TransactionMethodType transactionMethod);

    WalletEntity deductMoneyFromWallet(UserAccountEntity userAccountEntity, BigDecimal amount,
                                       String transactionId, OrderEntity orderEntity,
                                       TransactionMethodType transactionMethod);

    void withdrawAllMoneyFromWallet();

    WalletEntity findWalletById(UUID walletId);

    WalletEntity createNewWallet(UserAccountEntity userAccountEntity);

    WalletEntity getByUser(UserAccountEntity userAccountEntity);
}
