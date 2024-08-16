package com.github.foodie.event;

import com.github.foodie.dtos.OrderRequestDto;
import com.github.foodie.dtos.ShipperDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptOrderEvent {
    private ShipperDto shipperDto;
    private OrderRequestDto orderRequestDto;
}
