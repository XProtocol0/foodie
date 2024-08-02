package com.github.foodie.strategies;

import com.github.foodie.entities.ShipperEntity;
import org.locationtech.jts.geom.Point;

import java.util.List;

public interface ShipperMatchingStrategy {
    List<ShipperEntity> findMatchingShipper(Point restaurantLocation);
}
