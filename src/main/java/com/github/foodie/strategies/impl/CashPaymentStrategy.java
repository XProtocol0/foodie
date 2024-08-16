package com.github.foodie.strategies.impl;

import com.github.foodie.constants.PaymentStatusType;
import com.github.foodie.constants.TransactionMethodType;
import com.github.foodie.entities.OrderEntity;
import com.github.foodie.entities.PaymentEntity;
import com.github.foodie.entities.ShipperEntity;
import com.github.foodie.services.PaymentService;
import com.github.foodie.services.ShipperService;
import com.github.foodie.services.WalletService;
import com.github.foodie.strategies.PaymentStrategy;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class CashPaymentStrategy implements PaymentStrategy {

    @Value("${platform.commission}")
    private BigDecimal PLATFORM_COMMISSION;

    @Lazy
    @Autowired
    private WalletService walletService;
    @Lazy
    @Autowired
    private PaymentService paymentService;
    @Lazy
    @Autowired
    private ShipperService shipperService;

    @Override
    public void processPayment(PaymentEntity paymentEntity) {
        LogstashMarker markers = append("method", "processPayment");
        log.info(markers, "processing payment using cash strategy");
        OrderEntity orderEntity = paymentEntity.getOrderEntity();
        ShipperEntity shipperEntity = shipperService.getShipperById(orderEntity.getShipperId());
        BigDecimal platformCommission = paymentEntity.getAmount().multiply(PLATFORM_COMMISSION);
        walletService.deductMoneyFromWallet(shipperEntity.getUserAccountEntity(), platformCommission,
                null, orderEntity, TransactionMethodType.SHIPPER);
        paymentService.updatePaymentStatus(paymentEntity.getId(), PaymentStatusType.CONFIRMED);
    }
}
