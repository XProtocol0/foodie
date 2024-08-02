package com.github.foodie.strategies;

import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;

public interface DeliveryChargeCalculationStrategy {
    BigDecimal calculateCharge(Point from, Point to);
}
