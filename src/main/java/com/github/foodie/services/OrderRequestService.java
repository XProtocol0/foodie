package com.github.foodie.services;

import com.github.foodie.constants.OrderRequestStatusType;
import com.github.foodie.controllers.request.OrderRequestReq;
import com.github.foodie.dtos.CustomerDto;
import com.github.foodie.dtos.OrderRequestDto;
import com.github.foodie.dtos.ShipperDto;
import com.github.foodie.entities.OrderRequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface OrderRequestService {
    void requestOrder(OrderRequestReq orderRequestReq);
    OrderRequestEntity getRequestOrderById(UUID orderRequestId);
    OrderRequestEntity updateOrderRequestStatus(UUID orderRequestId, OrderRequestStatusType orderRequestStatusType);
}
