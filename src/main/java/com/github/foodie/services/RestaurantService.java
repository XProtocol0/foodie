package com.github.foodie.services;

import com.github.foodie.controllers.request.RegisterRestaurantReq;
import com.github.foodie.entities.RestaurantEntity;

import java.util.UUID;

public interface RestaurantService {
    UUID registerRestaurant(RegisterRestaurantReq registerRestaurantReq);
    RestaurantEntity getRestaurant(UUID restaurantId);
}
