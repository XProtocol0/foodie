package com.github.foodie.services;

import com.github.foodie.dtos.MenuDto;
import com.github.foodie.entities.RestaurantEntity;

import java.util.List;
import java.util.UUID;

public interface MenuService {
    void createAndSaveMenuEntity(List<MenuDto> menuDtoList, RestaurantEntity restaurantEntity);
    List<MenuDto> getItemsByRestaurant(UUID restaurantId);
}
