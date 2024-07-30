package com.github.foodie.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "wallet")
public class WalletEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccountEntity userAccountEntity;

    @Column(name = "user_id", updatable = false, insertable = false)
    private Long userId;

    @Column(name = "amount")
    private BigDecimal amount;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WalletTransactionEntity> walletTransactionEntities;

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
