package com.github.foodie.entities;

import com.github.foodie.constants.OrderRequestStatusType;
import com.github.foodie.constants.PaymentType;
import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "order_request")
public class OrderRequestEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "restaurant_location", columnDefinition = "Geometry(Point, 4326)")
    private Point restaurantLocation;

    @Column(name = "delivery_location", columnDefinition = "Geometry(Point, 4326)")
    private Point deliveryLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customerEntity;

    @Column(name = "customer_id", updatable = false, insertable = false)
    private UUID customerId;

    @Column(name = "restaurant_id")
    private UUID restaurantId;

    @Column(name = "items", columnDefinition = "jsonb")
    private Object items;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_request_status")
    private OrderRequestStatusType orderRequestStatusType;

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
