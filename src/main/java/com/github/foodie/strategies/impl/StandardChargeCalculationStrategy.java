package com.github.foodie.strategies.impl;

import com.github.foodie.services.DistanceService;
import com.github.foodie.strategies.DeliveryChargeCalculationStrategy;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class StandardChargeCalculationStrategy implements DeliveryChargeCalculationStrategy {

    @Value("${ride.fare.multiplier}")
    private BigDecimal RIDE_FARE_MULTIPLIER;

    @Autowired
    private DistanceService distanceService;

    @Override
    public BigDecimal calculateCharge(Point from, Point to) {
        LogstashMarker markers = append("method", "calculateCharge");
        log.info(markers, "calculating standard delivery charge");
        BigDecimal distance = distanceService.calculateDistance(from, to);
        return distance.multiply(RIDE_FARE_MULTIPLIER);
    }
}
