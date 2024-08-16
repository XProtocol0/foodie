package com.github.foodie.repositories;

import com.github.foodie.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    PaymentEntity findByOrderId(UUID orderId);
}
