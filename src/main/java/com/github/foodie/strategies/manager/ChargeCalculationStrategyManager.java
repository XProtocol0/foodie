package com.github.foodie.strategies.manager;

import com.github.foodie.strategies.DeliveryChargeCalculationStrategy;
import com.github.foodie.strategies.impl.StandardChargeCalculationStrategy;
import com.github.foodie.strategies.impl.SurgeChargeCalculationStrategy;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

import static net.logstash.logback.marker.Markers.append;

@Component
@Slf4j
public class ChargeCalculationStrategyManager {
    @Autowired
    private StandardChargeCalculationStrategy standardChargeCalculationStrategy;
    @Autowired
    private SurgeChargeCalculationStrategy surgeChargeCalculationStrategy;


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
