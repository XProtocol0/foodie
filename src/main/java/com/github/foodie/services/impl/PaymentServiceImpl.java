package com.github.foodie.services.impl;

import com.github.foodie.constants.PaymentStatusType;
import com.github.foodie.constants.PaymentType;
import com.github.foodie.entities.OrderEntity;
import com.github.foodie.entities.PaymentEntity;
import com.github.foodie.exceptions.Errors;
import com.github.foodie.exceptions.ServiceException;
import com.github.foodie.repositories.PaymentRepository;
import com.github.foodie.services.MenuService;
import com.github.foodie.services.PaymentService;
import com.github.foodie.strategies.manager.PaymentStrategyManager;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MenuService menuService;
    @Autowired
    private PaymentStrategyManager paymentStrategyManager;

    @Override
    @Transactional
    public PaymentEntity createPaymentEntity(OrderEntity orderEntity, PaymentType paymentType, BigDecimal extraCharges) {
        LogstashMarker markers = append("method", "createPaymentEntity");
        log.info(markers, "creating payment entity");

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setOrderEntity(orderEntity);
        paymentEntity.setOrderId(orderEntity.getId());
        paymentEntity.setPaymentType(paymentType);
        paymentEntity.setPaymentStatus(PaymentStatusType.PENDING);
        paymentEntity.setAmount(getTotalAmount(extraCharges,
                orderEntity.getOrderRequestEntity().getItems()));
        paymentRepository.save(paymentEntity);
        return paymentEntity;
    }

    @Override
    public PaymentEntity processPayment(OrderEntity orderEntity) {
        LogstashMarker markers = append("method", "processPayment");
        log.info(markers, "processing payment for order: {}", orderEntity.getId());

        PaymentEntity paymentEntity = paymentRepository.findByOrderId(orderEntity.getId());
        if(Objects.isNull(paymentEntity)) {
            log.info(markers, "no payment found for order: {}", orderEntity.getId());
            throw ServiceException.notFound(Errors.PAYMENT_NOT_FOUND,
                    Errors.errorMap.get(Errors.PAYMENT_NOT_FOUND));
        }
        paymentStrategyManager.paymentStrategy(paymentEntity.getPaymentType()).processPayment(paymentEntity);
        return paymentEntity;
    }

    @Override
    @Transactional
    public PaymentEntity updatePaymentStatus(UUID paymentId, PaymentStatusType paymentStatusType) {
        LogstashMarker markers = append("method", "updatePaymentStatus");
        log.info(markers, "updating payment status of id: {} to status: {}", paymentId, paymentStatusType);
        PaymentEntity paymentEntity = getPaymentById(paymentId);
        paymentEntity.setPaymentStatus(paymentStatusType);
        if(PaymentStatusType.CONFIRMED.equals(paymentStatusType)) {
            paymentEntity.setPaymentTime(Instant.now());
        }
        paymentRepository.save(paymentEntity);
        return paymentEntity;
    }


    private BigDecimal getTotalAmount(BigDecimal extraCharges, List<UUID> menuItems) {
        LogstashMarker markers = append("method", "getTotalAmount");
        log.info(markers, "getting total payment amount");
        BigDecimal itemsAmount = menuService.getTotalAmountOfItems(menuItems);
        return itemsAmount.add(extraCharges);
    }

    private PaymentEntity getPaymentById(UUID paymentId) {
        LogstashMarker markers = append("method", "getPaymentById");
        log.info(markers, "getting payment by id: {}", paymentId);

        PaymentEntity paymentEntity = paymentRepository.findById(paymentId).orElse(null);
        if(Objects.isNull(paymentEntity)) {
            log.info(markers, "no payment found with id: {}", paymentId);
            throw ServiceException.notFound(Errors.PAYMENT_NOT_FOUND,
                    Errors.errorMap.get(Errors.PAYMENT_NOT_FOUND));
        }
        return paymentEntity;
    }
}
