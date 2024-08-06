package com.github.foodie.entities;

import com.github.foodie.constants.PaymentMethodType;
import com.github.foodie.constants.PaymentStatusType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "payment")
public class PaymentEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethod;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @Column(name = "order_id", insertable=false, updatable=false)
    private UUID orderId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatusType paymentStatus;

    @Column(name = "payment_time")
    private Instant paymentTime;

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
