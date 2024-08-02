package com.github.foodie.controllers;

import com.github.foodie.controllers.request.RegisterRestaurantReq;
import com.github.foodie.controllers.response.GenericResponse;
import com.github.foodie.services.RestaurantService;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static net.logstash.logback.marker.Markers.append;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<UUID>> registerRestaurant(@RequestBody RegisterRestaurantReq req) {
        LogstashMarker markers = append("method", "registerRestaurant");
        UUID id = restaurantService.registerRestaurant(req);
        return ResponseEntity.ok(new GenericResponse<>(id));
    }
}
