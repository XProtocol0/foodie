package com.github.foodie.services;

import com.github.foodie.constants.PaymentStatusType;
import com.github.foodie.constants.PaymentType;
import com.github.foodie.entities.OrderEntity;
import com.github.foodie.entities.PaymentEntity;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentService {
    PaymentEntity createPaymentEntity(OrderEntity orderEntity,
                                      PaymentType paymentType,
                                      BigDecimal extraCharges);
    PaymentEntity processPayment(OrderEntity orderEntity);
    PaymentEntity updatePaymentStatus(UUID paymentId, PaymentStatusType paymentStatusType);
}
