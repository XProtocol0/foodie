package com.github.foodie.services;


import com.github.foodie.constants.OrderStatusType;
import com.github.foodie.dtos.CustomerDto;
import com.github.foodie.dtos.OrderRequestDto;
import com.github.foodie.dtos.ShipperDto;
import com.github.foodie.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderEntity createOrder(OrderRequestDto orderRequestDto, ShipperDto shipperDto);
    Page<CustomerDto> getAllOrdersOfCustomer(UUID customerId, PageRequest pageRequest);
    Page<ShipperDto> getAllOrdersOfShipper(UUID shipperId, PageRequest pageRequest);
    OrderEntity getOrderById(UUID orderId);
    List<OrderEntity> getOrderByOrderRequestId(UUID orderRequestId);
    OrderEntity updateOrderStatus(UUID orderId, OrderStatusType orderStatus);
    UUID getActiveOrderByShipperId(UUID shipperId);
}
