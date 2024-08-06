package com.github.foodie.strategies.impl;

import com.github.foodie.entities.ShipperEntity;
import com.github.foodie.services.ShipperService;
import com.github.foodie.strategies.ShipperMatchingStrategy;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class NearestShipperStrategy implements ShipperMatchingStrategy {

    @Autowired
    private ShipperService shipperService;

    @Override
    public List<ShipperEntity> findMatchingShipper(Point restaurantLocation) {
        LogstashMarker markers = append("method", "findMatchingShipper");
        log.info(markers, "finding matching shipper");
        return shipperService.getTenNearestShipper(restaurantLocation);
    }
}
