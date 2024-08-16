package com.github.foodie.entities;

import com.github.foodie.constants.TransactionMethodType;
import com.github.foodie.constants.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "wallet_transaction")
public class WalletTransactionEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_method")
    private TransactionMethodType transactionMethodType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @Column(name = "order_id", updatable = false, insertable = false)
    private UUID orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private WalletEntity wallet;

    @Column(name = "wallet_id", updatable = false, insertable = false)
    private UUID walletId;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @PrePersist
    protected void onCreate() {
        this.createdOn = this.updatedOn = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedOn = Instant.now();
    }
}
