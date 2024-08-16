package com.github.foodie.services;

import com.github.foodie.controllers.request.RegisterShipperReq;
import com.github.foodie.dtos.OrderDto;
import com.github.foodie.dtos.OrderRequestDto;
import com.github.foodie.entities.ShipperEntity;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.UUID;

public interface ShipperService {
    void registerShipper(RegisterShipperReq req);
    void cancelOrderDelivery(UUID orderRequestId);
    void acceptOrderDelivery(UUID orderRequestId);
    OrderDto orderDelivered(UUID orderRequestId);
    OrderRequestDto rateCustomer(UUID orderRequestId, Integer rating);
    ShipperEntity getShipperById(UUID id);
    List<OrderRequestDto> getAllOrder();
    List<ShipperEntity> getTenNearestShipper(Point location);
    List<ShipperEntity> getTenNearbyHighestRatedShipper(Point location);
    ShipperEntity getShipperByUserId(Long userId);
    OrderDto updateOrderAfterPickUp(UUID orderId);
    UUID getActiveOrder();
}
