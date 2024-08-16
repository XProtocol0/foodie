package com.github.foodie.services.impl;

import com.github.foodie.dtos.MenuDto;
import com.github.foodie.entities.MenuEntity;
import com.github.foodie.entities.RestaurantEntity;
import com.github.foodie.exceptions.Errors;
import com.github.foodie.exceptions.ServiceException;
import com.github.foodie.repositories.MenuRepository;
import com.github.foodie.services.MenuService;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public void createAndSaveMenuEntity(List<MenuDto> menuDtoList, RestaurantEntity restaurantEntity) {
        List<MenuEntity> menuEntityList = new ArrayList<>();
        for(MenuDto dto : menuDtoList) {
            MenuEntity menuEntity = new MenuEntity();
            menuEntity.setRestaurantEntity(restaurantEntity);
            menuEntity.setRestaurantId(restaurantEntity.getId());
            menuEntity.setItemName(dto.getItemName());
            menuEntity.setPrice(dto.getPrice());
            menuEntity.setIngredients(dto.getIngredients());
            menuEntity.setVegetarian(dto.getVegetarian());
            menuEntityList.add(menuEntity);
        }
        menuRepository.saveAll(menuEntityList);
    }

    @Override
    public List<MenuDto> getItemsByRestaurant(UUID restaurantId) {
        LogstashMarker marker = append("method", "getItemsByRestaurant");
        log.info(marker, "getting menu items with restaurantId: {}", restaurantId);
        List<MenuEntity> menuEntityList = menuRepository.findAllByRestaurantId(restaurantId);
        if(CollectionUtils.isEmpty(menuEntityList)) {
            log.info(marker, "no menu item found for restaurantId: {}", restaurantId);
            throw ServiceException.badRequest(
                    Errors.MENU_ITEM_NOT_FOUND, Errors.errorMap.get(Errors.MENU_ITEM_NOT_FOUND));
        }

        return menuEntityList.stream().map(MenuDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalAmountOfItems(List<UUID> menuItems) {
        LogstashMarker marker = append("method", "getTotalAmountOfItems");
        log.info(marker, "getting total amount of items");
        List<MenuEntity> menuEntityList = new ArrayList<>();
        try{
            menuEntityList = menuRepository.findAllById(menuItems);
        } catch (Exception ex) {
            log.info(marker, "got exception while fetching menu items");
            log.info(marker, "exception: {}", ExceptionUtils.getStackTrace(ex));
            throw ServiceException.internalError(Errors.INTERNAL_SERVER_ERROR,
                    Errors.errorMap.get(Errors.INTERNAL_SERVER_ERROR));
        }
        if(CollectionUtils.isEmpty(menuEntityList)) {
            log.info(marker, "no menu item found found for: {}", menuItems);
            throw ServiceException.badRequest(
                    Errors.MENU_ITEM_NOT_FOUND, Errors.errorMap.get(Errors.MENU_ITEM_NOT_FOUND));
        }
        return menuEntityList.stream()
                .map(MenuEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
