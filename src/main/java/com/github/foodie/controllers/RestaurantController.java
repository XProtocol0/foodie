package com.github.foodie.controllers;

import com.github.foodie.controllers.request.RegisterRestaurantReq;
import com.github.foodie.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/register")
    public ResponseEntity<UUID> registerRestaurant(@RequestBody RegisterRestaurantReq req) {
        UUID id = restaurantService.registerRestaurant(req);
        return ResponseEntity.ok(id);
    }
}
