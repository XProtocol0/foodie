package com.github.foodie.client;

import com.github.foodie.dtos.osrm.OSRMResponseDto;
import com.github.foodie.exceptions.Errors;
import com.github.foodie.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class OSRMClient {

    @Value("${osrm.base.url}")
    private String baseUrl;
    @Value("${osrm.route.endpoint}")
    private String routeEndpoint;

    public OSRMResponseDto getRoutesDistance(Point src, Point dest) {
        LogstashMarker markers = append("method", "getRoutesDistance");
        log.info(markers, "getting routes from src: {} to dest: {}", src, dest);
        try {
            String uri = routeEndpoint + src.getX() + "," + src.getY() + ";" +
                    dest.getX() + "," + dest.getY();
            return RestClient.builder()
                    .baseUrl(baseUrl)
                    .build()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .body(OSRMResponseDto.class);
        } catch (Exception e) {
            log.info(markers, "error while getting route distance from OSRM");
            throw ServiceException.externalError(Errors.OSRM_ROUTE_DISTANCE_ERROR,
                    Errors.errorMap.get(Errors.OSRM_ROUTE_DISTANCE_ERROR));
        }
    }


}
