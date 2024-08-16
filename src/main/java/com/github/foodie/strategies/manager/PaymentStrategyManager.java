package com.github.foodie.strategies.manager;

import com.github.foodie.constants.PaymentType;
import com.github.foodie.strategies.PaymentStrategy;
import com.github.foodie.strategies.impl.CashPaymentStrategy;
import com.github.foodie.strategies.impl.WalletPaymentStrategy;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static net.logstash.logback.marker.Markers.append;

@Component
@Slf4j
public class PaymentStrategyManager {
    @Autowired
    private CashPaymentStrategy cashPaymentStrategy;
    @Autowired
    private WalletPaymentStrategy walletPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentType paymentType) {
        LogstashMarker markers = append("method", "paymentStrategy");
        log.info(markers, "deciding payment strategy");

        return switch (paymentType) {
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
    }
}
