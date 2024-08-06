package com.github.foodie.services;

import com.github.foodie.controllers.request.RegisterShipperReq;
import com.github.foodie.dtos.OrderRequestDto;
import com.github.foodie.dtos.ShipperDto;
import com.github.foodie.entities.ShipperEntity;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.UUID;

public interface ShipperService {
    void registerShipper(RegisterShipperReq req);
    OrderRequestDto cancelOrderDelivery(UUID orderRequestId);
    OrderRequestDto acceptOrderDelivery(UUID orderRequestId);
    OrderRequestDto orderDelivered(UUID orderRequestId);
    OrderRequestDto rateCustomer(UUID orderRequestId, Integer rating);
    ShipperDto getProfile();
    List<OrderRequestDto> getAllOrder();
    List<ShipperEntity> getTenNearestShipper(Point location);
    List<ShipperEntity> getTenNearbyHighestRatedShipper(Point location);
}
