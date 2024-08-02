package com.github.foodie.controllers;

import com.github.foodie.dtos.MenuDto;
import com.github.foodie.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<List<MenuDto>> getItemsByRestaurant(@RequestParam UUID restaurantId) {
        return ResponseEntity.ok(menuService.getItemsByRestaurant(restaurantId));
    }
}
