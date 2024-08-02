package com.github.foodie.controllers.request;

import com.github.foodie.dtos.MenuDto;
import com.github.foodie.dtos.PointDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRestaurantReq {
    private Long userId;
    private String address;
    private String zipcode;
    private Integer openingHour;
    private Integer closingHour;
    private BigDecimal rating;
    private PointDto location;
    private List<MenuDto> menu;
}
