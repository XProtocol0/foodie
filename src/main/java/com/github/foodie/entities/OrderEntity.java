package com.github.foodie.entities;

import com.github.foodie.constants.OrderStatusType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_request_id")
    private OrderRequestEntity orderRequestEntity;

    @Column(name = "order_request_id", updatable = false, insertable = false)
    private UUID orderRequestId;

    @Column(name = "shipper_id")
    private UUID shipperId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatusType orderStatus;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private PaymentEntity paymentEntity;

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
