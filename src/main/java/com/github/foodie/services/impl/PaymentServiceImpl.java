package com.github.foodie.services.impl;

import com.github.foodie.constants.PaymentMethodType;
import com.github.foodie.constants.PaymentStatusType;
import com.github.foodie.controllers.request.OrderRequestReq;
import com.github.foodie.entities.OrderEntity;
import com.github.foodie.entities.PaymentEntity;
import com.github.foodie.repositories.PaymentRepository;
import com.github.foodie.services.MenuService;
import com.github.foodie.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MenuService menuService;

    @Override
    @Transactional
    public PaymentEntity createPaymentEntity(OrderEntity orderEntity, OrderRequestReq orderRequestReq, BigDecimal extraCharges) {
        LogstashMarker markers = append("method", "createPaymentEntity");
        log.info(markers, "creating payment entity");

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setOrderEntity(orderEntity);
        paymentEntity.setOrderId(orderEntity.getId());
        paymentEntity.setPaymentMethod(PaymentMethodType.valueOf(orderRequestReq.getPaymentType()));
        paymentEntity.setPaymentStatus(PaymentStatusType.PENDING);
        paymentEntity.setAmount(getTotalAmount(extraCharges,
                orderEntity.getOrderRequestEntity().getItems()));
        paymentRepository.save(paymentEntity);
        return paymentEntity;
    }

    @Override
    public BigDecimal getTotalAmount(BigDecimal extraCharges, List<UUID> menuItems) {
        LogstashMarker markers = append("method", "getTotalAmount");
        log.info(markers, "getting total payment amount");
        BigDecimal itemsAmount = menuService.getTotalAmountOfItems(menuItems);
        return itemsAmount.add(extraCharges);
    }
}
