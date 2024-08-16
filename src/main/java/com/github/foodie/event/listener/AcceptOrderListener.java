package com.github.foodie.event.listener;

import com.github.foodie.dtos.OrderRequestDto;
import com.github.foodie.entities.OrderEntity;
import com.github.foodie.entities.PaymentEntity;
import com.github.foodie.event.AcceptOrderEvent;
import com.github.foodie.services.OrderService;
import com.github.foodie.services.PaymentService;
import com.github.foodie.strategies.manager.ChargeCalculationStrategyManager;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;

import static net.logstash.logback.marker.Markers.append;

@Slf4j
@Component
public class AcceptOrderListener extends AbstractEventListener{

    @Autowired
    private OrderService orderService;
    @Autowired
    private ChargeCalculationStrategyManager chargeCalculationStrategyManager;
    @Autowired
    private PaymentService paymentService;

    @Async
    @EventListener(value = AcceptOrderEvent.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createOrderAndPayment(AcceptOrderEvent event) {
        LogstashMarker markers = append("method", "createOrderAndPayment");
        log.info(markers, "creating order and payment after accept order event");
        OrderEntity orderEntity = orderService.createOrder(event.getOrderRequestDto(), event.getShipperDto());

        OrderRequestDto orderRequestDto = event.getOrderRequestDto();
        BigDecimal deliveryCharge = chargeCalculationStrategyManager.deliveryChargeCalculationStrategy().calculateCharge(
                orderRequestDto.getRestaurantLocation(), orderRequestDto.getDeliveryLocation());
        PaymentEntity paymentEntity =
                paymentService.createPaymentEntity(orderEntity, orderRequestDto.getPaymentType(), deliveryCharge);
        orderEntity.setPaymentEntity(paymentEntity);
    }
}
