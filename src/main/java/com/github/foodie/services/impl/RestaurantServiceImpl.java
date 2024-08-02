package com.github.foodie.services.impl;

import com.github.foodie.controllers.request.RegisterRestaurantReq;
import com.github.foodie.entities.RestaurantEntity;
import com.github.foodie.entities.UserAccountEntity;
import com.github.foodie.exceptions.Errors;
import com.github.foodie.exceptions.ServiceException;
import com.github.foodie.repositories.RestaurantRepository;
import com.github.foodie.services.MenuService;
import com.github.foodie.services.RestaurantService;
import com.github.foodie.services.UserAccountService;
import com.github.foodie.utils.GeometryUtil;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private MenuService menuService;

    @Override
    public UUID registerRestaurant(RegisterRestaurantReq req) {
        RestaurantEntity restaurantEntity = createAndSaveRestaurantEntity(req);
        menuService.createAndSaveMenuEntity(req.getMenu(), restaurantEntity);
        return restaurantEntity.getId();
    }

    private RestaurantEntity createAndSaveRestaurantEntity(RegisterRestaurantReq req) {
        UserAccountEntity userAccountEntity = userAccountService.getUserById(req.getUserId());
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setUserAccountEntity(userAccountEntity);
        restaurantEntity.setUserId(req.getUserId());
        restaurantEntity.setAddress(req.getAddress());
        restaurantEntity.setRating(req.getRating());
        restaurantEntity.setOpeningHour(req.getOpeningHour());
        restaurantEntity.setClosingHour(req.getClosingHour());
        restaurantEntity.setLocation(GeometryUtil.createPoint(req.getLocation()));
        restaurantRepository.save(restaurantEntity);
        return restaurantEntity;
    }

    @Override
    public RestaurantEntity getRestaurant(UUID restaurantId) {
        LogstashMarker marker = append("method", "getRestaurant");
        log.info(marker, "getting restaurant with id: {}", restaurantId);
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId).orElse(null);
        if(Objects.isNull(restaurantEntity)){
            log.info(marker, "restaurant not found for restaurantId: {}", restaurantId);
            throw ServiceException.badRequest(
                    Errors.RESTAURANT_NOT_FOUND, Errors.errorMap.get(Errors.RESTAURANT_NOT_FOUND));
        }
        return restaurantEntity;
    }
}
