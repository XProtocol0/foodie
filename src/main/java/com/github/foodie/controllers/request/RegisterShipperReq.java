package com.github.foodie.controllers.request;

import com.github.foodie.dtos.PointDto;
import lombok.Data;


@Data
public class RegisterShipperReq {
    private Long userId;
    private PointDto location;
}
