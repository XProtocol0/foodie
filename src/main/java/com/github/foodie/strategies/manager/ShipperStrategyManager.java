package com.github.foodie.strategies.manager;

import com.github.foodie.strategies.ShipperMatchingStrategy;
import com.github.foodie.strategies.impl.*;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static net.logstash.logback.marker.Markers.append;

@Component
@Slf4j
public class ShipperStrategyManager {

    @Autowired
    private HighestRatedShipperStrategy highestRatedShipperStrategy;
    @Autowired
    private NearestShipperStrategy nearestShipperStrategy;


    public ShipperMatchingStrategy shipperMatchingStrategy(BigDecimal customerRating) {
        LogstashMarker markers = append("method", "shipperMatchingStrategy");
        log.info(markers, "deciding shipper matching strategy");
        if(customerRating.compareTo(BigDecimal.valueOf(4.8)) > 0) {
            log.info(markers, "highest rated shipper strategy chosen");
            return highestRatedShipperStrategy;
        } else {
            log.info(markers, "nearest shipper strategy chosen");
            return nearestShipperStrategy;
        }
    }


}
