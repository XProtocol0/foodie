package com.github.foodie.controllers;

import com.github.foodie.dtos.MenuDto;
import com.github.foodie.services.MenuService;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static net.logstash.logback.marker.Markers.append;

@RestController
@RequestMapping("menu")
@Slf4j
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<List<MenuDto>> getItemsByRestaurant(@RequestParam UUID restaurantId) {
        LogstashMarker markers = append("method", "getItemsByRestaurant");
        log.info(markers, "Getting menu items for restaurant with id: {}", restaurantId);
        return ResponseEntity.ok(menuService.getItemsByRestaurant(restaurantId));
    }
}
