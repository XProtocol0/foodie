package com.github.foodie.services.impl;

import com.github.foodie.client.OSRMClient;
import com.github.foodie.dtos.osrm.OSRMResponseDto;
import com.github.foodie.exceptions.Errors;
import com.github.foodie.exceptions.ServiceException;
import com.github.foodie.services.DistanceService;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class OSRMDistanceServiceImpl implements DistanceService {

    @Autowired
    private OSRMClient osrmClient;

    @Override
    public BigDecimal calculateDistance(Point src, Point dest) {
        LogstashMarker markers = append("method", "calculateDistance");
        log.info(markers, "getting distance via osrm client");
        OSRMResponseDto osrmResponseDto = osrmClient.getRoutesDistance(src, dest);

        if(CollectionUtils.isEmpty(osrmResponseDto.getRoutes())) {
            log.info(markers, "osrm couldn't find any routes for src: {}, dest: {}", src, dest);
            throw ServiceException.externalError(Errors.OSRM_NO_ROUTE_FOUND,
                    Errors.errorMap.get(Errors.OSRM_NO_ROUTE_FOUND));
        }

        return osrmResponseDto.getRoutes().get(0).getDistance()
                .divide(BigDecimal.valueOf(1000), RoundingMode.FLOOR);
    }
}
