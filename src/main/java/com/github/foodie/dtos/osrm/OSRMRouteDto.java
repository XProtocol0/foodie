package com.github.foodie.dtos.osrm;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OSRMRouteDto {
    private BigDecimal distance;
}
