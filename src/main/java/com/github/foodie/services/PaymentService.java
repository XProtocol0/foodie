package com.github.foodie.services;

import com.github.foodie.controllers.request.OrderRequestReq;
import com.github.foodie.entities.OrderEntity;
import com.github.foodie.entities.PaymentEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PaymentService {
    PaymentEntity createPaymentEntity(OrderEntity orderEntity,
                                      OrderRequestReq orderRequestReq,
                                      BigDecimal extraCharges);
    BigDecimal getTotalAmount(BigDecimal extraCharges, List<UUID> menuItems);
}
