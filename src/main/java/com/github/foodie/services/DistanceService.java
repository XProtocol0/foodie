package com.github.foodie.services;

import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;

public interface DistanceService {
    BigDecimal calculateDistance(Point src, Point dest);
}
