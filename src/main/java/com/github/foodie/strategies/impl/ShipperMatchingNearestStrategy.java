package com.github.foodie.strategies.impl;

import com.github.foodie.entities.ShipperEntity;
import com.github.foodie.repositories.ShipperRepository;
import com.github.foodie.strategies.ShipperMatchingStrategy;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipperMatchingNearestStrategy implements ShipperMatchingStrategy {

    @Autowired
    private ShipperRepository shipperRepository;

    @Override
    public List<ShipperEntity> findMatchingShipper(Point restaurantLocation) {

        return List.of();
    }
}
