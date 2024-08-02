package com.github.foodie.services;

import com.github.foodie.controllers.request.RegisterUserReq;
import com.github.foodie.dtos.CustomerDto;
import com.github.foodie.dtos.OrderRequestDto;
import com.github.foodie.entities.CustomerEntity;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    CustomerEntity registerCustomer(Long userId);
    CustomerEntity getCustomer(UUID customerId);
    OrderRequestDto cancelOrder(UUID orderRequestId);
    void rateShipper(UUID orderRequestId, Integer rating);
    CustomerDto getProfile();
    List<OrderRequestDto> getAllOrders();
}
