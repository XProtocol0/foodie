package com.github.foodie.strategies.impl;

import com.github.foodie.dtos.OrderRequestDto;
import com.github.foodie.strategies.DeliveryChargeCalculationStrategy;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;

public class StandardChargeCalculationStrategy implements DeliveryChargeCalculationStrategy {
    @Override
    public BigDecimal calculateCharge(Point from, Point to) {
        return BigDecimal.TEN;
    }
}
