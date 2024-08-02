package com.github.foodie.strategies.impl;

import com.github.foodie.strategies.DeliveryChargeCalculationStrategy;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SurgeChargeCalculationStrategy implements DeliveryChargeCalculationStrategy {
    @Override
    public BigDecimal calculateCharge(Point from, Point to) {
        return BigDecimal.ONE;
    }
}
