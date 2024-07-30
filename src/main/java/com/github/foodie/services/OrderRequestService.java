package com.github.foodie.services;

import com.github.foodie.constants.OrderRequestStatusType;
import com.github.foodie.dtos.CustomerDto;
import com.github.foodie.dtos.OrderRequestDto;
import com.github.foodie.dtos.ShipperDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface OrderRequestService {
    OrderRequestDto getOrderById(UUID orderRequestId);
    void findShipper(OrderRequestDto orderRequestDto);
    OrderRequestDto updateOrderRequestStatus(UUID orderRequest, OrderRequestStatusType orderRequestStatusType);
    Page<CustomerDto> getAllOrdersOfCustomer(UUID customerId, PageRequest pageRequest);
    Page<ShipperDto> getAllOrdersOfShipper(UUID shipperId, PageRequest pageRequest);
}
