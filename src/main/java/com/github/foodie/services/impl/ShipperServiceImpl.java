package com.github.foodie.services.impl;

import com.github.foodie.controllers.request.RegisterShipperReq;
import com.github.foodie.dtos.OrderRequestDto;
import com.github.foodie.dtos.ShipperDto;
import com.github.foodie.entities.ShipperEntity;
import com.github.foodie.entities.UserAccountEntity;
import com.github.foodie.repositories.ShipperRepository;
import com.github.foodie.services.ShipperService;
import com.github.foodie.services.UserAccountService;
import com.github.foodie.utils.GeometryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ShipperServiceImpl implements ShipperService {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private ShipperRepository shipperRepository;

    @Override
    public void registerShipper(RegisterShipperReq req) {
        createAndSaveShipperEntity(req);
    }

    private void createAndSaveShipperEntity(RegisterShipperReq req) {
        UserAccountEntity userAccountEntity = userAccountService.getUserById(req.getUserId());
        ShipperEntity shipperEntity = new ShipperEntity();
        shipperEntity.setUserAccountEntity(userAccountEntity);
        shipperEntity.setUserId(userAccountEntity.getId());
        shipperEntity.setAvailable(Boolean.TRUE);
        shipperEntity.setRating(BigDecimal.ZERO);
        shipperEntity.setLocation(GeometryUtil.createPoint(req.getLocation()));
        shipperRepository.save(shipperEntity);
    }

    @Override
    public OrderRequestDto cancelOrderDelivery(UUID orderRequestId) {
        return null;
    }

    @Override
    public OrderRequestDto acceptOrderDelivery(UUID orderRequestId) {
        return null;
    }

    @Override
    public OrderRequestDto orderDelivered(UUID orderRequestId) {
        return null;
    }

    @Override
    public OrderRequestDto rateCustomer(UUID orderRequestId, Integer rating) {
        return null;
    }

    @Override
    public ShipperDto getProfile() {
        return null;
    }

    @Override
    public List<OrderRequestDto> getAllOrder() {
        return List.of();
    }
}
