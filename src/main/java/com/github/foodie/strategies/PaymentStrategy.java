package com.github.foodie.strategies;

import com.github.foodie.entities.PaymentEntity;

public interface PaymentStrategy {
    void processPayment(PaymentEntity paymentEntity);
}
