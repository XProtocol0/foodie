package com.github.foodie.services;

import com.github.foodie.controllers.request.OrderRequestReq;

public interface OrderService {
    void requestOrder(OrderRequestReq orderRequestReq);
}
