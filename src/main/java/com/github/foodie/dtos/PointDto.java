package com.github.foodie.dtos;

import com.github.foodie.constants.PointType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PointDto {
    private final String type = PointType.POINT.name();
    private double[] coordinates;
}
