package com.github.foodie.strategies;

import com.github.foodie.strategies.impl.HighestRatedShipperStrategy;
import com.github.foodie.strategies.impl.NearestShipperStrategy;
import com.github.foodie.strategies.impl.StandardChargeCalculationStrategy;
import com.github.foodie.strategies.impl.SurgeChargeCalculationStrategy;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;

import static net.logstash.logback.marker.Markers.append;

@Component
@Slf4j
public class StrategyManager {

    @Autowired
    private HighestRatedShipperStrategy highestRatedShipperStrategy;
    @Autowired
    private NearestShipperStrategy nearestShipperStrategy;
    @Autowired
    private StandardChargeCalculationStrategy standardChargeCalculationStrategy;
    @Autowired
    private SurgeChargeCalculationStrategy surgeChargeCalculationStrategy;

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

    public DeliveryChargeCalculationStrategy deliveryChargeCalculationStrategy() {
        LogstashMarker markers = append("method", "shipperMatchingStrategy");
        log.info(markers, "deciding delivery charge calculation strategy");
        LocalTime surgeStartTime = LocalTime.of(18, 0);
        LocalTime surgeEndTime = LocalTime.of(21, 0);
        LocalTime currentTime = LocalTime.now();

        if (currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime)) {
            log.info(markers, "surge charge strategy chosen");
            return surgeChargeCalculationStrategy;
        } else {
            log.info(markers, "standard charge strategy chosen");
            return standardChargeCalculationStrategy;
        }
    }
}
