package com.github.foodie.dtos;

import com.github.foodie.constants.PointType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PointDto {
    private final String type = PointType.POINT.name();
    @NotEmpty(message = "must provide coordinates")
    private double[] coordinates;
}
