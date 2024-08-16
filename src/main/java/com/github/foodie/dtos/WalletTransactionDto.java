package com.github.foodie.dtos;

import com.github.foodie.constants.TransactionMethodType;
import com.github.foodie.constants.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransactionDto {
    private UUID id;
    private BigDecimal amount;
    private TransactionType transactionType;
    private TransactionMethodType transactionMethodType;
    private OrderDto orderDto;
    private UUID walletId;
    private WalletDto walletDto;
    private String transactionId;
    private Instant createdOn;
    private Instant updatedOn;
}
