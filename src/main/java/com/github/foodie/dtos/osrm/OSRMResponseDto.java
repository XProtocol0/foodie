package com.github.foodie.dtos.osrm;

import lombok.Data;

import java.util.List;

@Data
public class OSRMResponseDto {
    private List<OSRMRouteDto> routes;
}
