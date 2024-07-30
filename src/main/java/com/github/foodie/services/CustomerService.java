package com.github.foodie.services;

import com.github.foodie.dtos.CustomerDto;
import com.github.foodie.dtos.OrderRequestDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    OrderRequestDto placeOrder(OrderRequestDto orderRequestDto);
    OrderRequestDto cancelOrder(UUID orderRequestId);
    void rateShipper(UUID orderRequestId, Integer rating);
    CustomerDto getProfile();
    List<OrderRequestDto> getAllOrders();
}
