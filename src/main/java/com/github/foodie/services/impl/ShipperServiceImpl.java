package com.github.foodie.services.impl;

import com.github.foodie.constants.OrderRequestStatusType;
import com.github.foodie.constants.OrderStatusType;
import com.github.foodie.controllers.request.RegisterShipperReq;
import com.github.foodie.dtos.OrderDto;
import com.github.foodie.dtos.OrderRequestDto;
import com.github.foodie.dtos.ShipperDto;
import com.github.foodie.entities.OrderEntity;
import com.github.foodie.entities.OrderRequestEntity;
import com.github.foodie.entities.ShipperEntity;
import com.github.foodie.entities.UserAccountEntity;
import com.github.foodie.event.AcceptOrderEvent;
import com.github.foodie.exceptions.Errors;
import com.github.foodie.exceptions.ServiceException;
import com.github.foodie.repositories.ShipperRepository;
import com.github.foodie.services.*;
import com.github.foodie.utils.GeometryUtil;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class ShipperServiceImpl implements ShipperService {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private ShipperRepository shipperRepository;
    @Autowired
    private OrderRequestService orderRequestService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;

    @Override
    public void registerShipper(RegisterShipperReq req) {
        LogstashMarker markers = append("method", "registerShipper");
        log.info(markers, "registering shipper with user id: {}", req.getUserId());
        createAndSaveShipperEntity(req);
    }

    @Transactional
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
    public void cancelOrderDelivery(UUID orderRequestId) {
        LogstashMarker markers = append("method", "cancelOrderDelivery");
        log.info(markers, "cancelling order request");
        //get shipperId by token
        UUID shipperId = UUID.randomUUID();

        OrderRequestEntity orderRequestEntity = orderRequestService.getRequestOrderById(orderRequestId);
        List<OrderEntity> orderEntityList = orderService.getOrderByOrderRequestId(orderRequestId);
        OrderEntity orderEntity = getAcceptedOrderForShipper(orderEntityList);

        orderRequestService.updateOrderRequestStatus(orderRequestEntity.getId(), OrderRequestStatusType.PENDING);
        orderService.updateOrderStatus(orderEntity.getId(), OrderStatusType.CANCELED);
        //todo: get the shippers and send them notification
    }

    @Override
    public void acceptOrderDelivery(UUID orderRequestId) {
        LogstashMarker markers = append("method", "acceptOrderDelivery");
        log.info(markers, "accepting order delivery for order request id: {}", orderRequestId);

        OrderRequestEntity entity = orderRequestService.getRequestOrderById(orderRequestId);

        if(!entity.getOrderRequestStatusType().equals(OrderRequestStatusType.PENDING)) {
            log.info(markers, "order request is not pending, status: {}", entity.getOrderRequestStatusType());
            throw ServiceException.badRequest(Errors.ORDER_REQUEST_NOT_PENDING,
                    Errors.errorMap.get(Errors.ORDER_REQUEST_NOT_PENDING));
        }

        //get shipper id from token
        ShipperEntity shipperEntity = getShipperByUserId(8L);

        if(Boolean.FALSE.equals(shipperEntity.getAvailable())) {
            log.info(markers, "can not accept because shipper is not available");
            throw ServiceException.badRequest(Errors.SHIPPER_NOT_AVAILABLE,
                    Errors.errorMap.get(Errors.SHIPPER_NOT_AVAILABLE));
        }

        entity = orderRequestService.updateOrderRequestStatus(orderRequestId, OrderRequestStatusType.CONFIRMED);
        shipperEntity = updateShipperAvailability(shipperEntity.getId(), Boolean.FALSE);
        createOrder(shipperEntity, entity);
    }

    @Override
    public ShipperEntity getShipperByUserId(Long userId) {
        LogstashMarker markers = append("method", "getShipperByUserId");
        log.info(markers, "getting shipper by user id: {}", userId);

        ShipperEntity shipperEntity = shipperRepository.findByUserId(userId);
        if(Objects.isNull(shipperEntity)) {
            log.info(markers, "couldn't find any shipper with user id: {}", userId);
            throw ServiceException.badRequest(Errors.SHIPPER_NOT_FOUND,
                    Errors.errorMap.get(Errors.SHIPPER_NOT_FOUND));
        }
        return shipperEntity;
    }

    @Override
    public OrderDto updateOrderAfterPickUp(UUID orderId) {
        LogstashMarker markers = append("method", "updateOrderAfterPickUp");
        log.info(markers, "updating order after delivery items have been picked up for order: {}", orderId);

        OrderEntity orderEntity = orderService.getOrderById(orderId);
        //todo: get shipper id by token
        UUID shipperId = UUID.fromString("6fdb2bac-4709-4957-a2b2-80d9b3811e2f");

        if(!orderEntity.getShipperId().equals(shipperId)) {
            log.info(markers, "order has not been assigned to shipper: {}", shipperId);
            throw ServiceException.badRequest(Errors.SHIPPER_NOT_ASSIGNED_TO_ORDER,
                    Errors.errorMap.get(Errors.SHIPPER_NOT_ASSIGNED_TO_ORDER));
        }

        if(!OrderStatusType.ACCEPTED.equals(orderEntity.getOrderStatus())) {
            log.info(markers, "order is not accept, so items can not be picked up by the shipper: {}", shipperId);
            throw ServiceException.badRequest(Errors.ORDER_NOT_ACCEPTED,
                    Errors.errorMap.get(Errors.ORDER_NOT_ACCEPTED));
        }

        orderEntity = orderService.updateOrderStatus(orderEntity.getId(), OrderStatusType.PICKED_UP);
        //todo: send notification to customer
        OrderRequestEntity orderRequestEntity = orderEntity.getOrderRequestEntity();
        return OrderDto.fromEntity(orderEntity);
    }

    @Override
    public UUID getActiveOrder() {
        LogstashMarker markers = append("method", "getActiveOrder");
        UUID shipperId = UUID.fromString("6fdb2bac-4709-4957-a2b2-80d9b3811e2f");
        return orderService.getActiveOrderByShipperId(shipperId);
    }

    @Override
    public OrderDto orderDelivered(UUID orderId) {
        LogstashMarker markers = append("method", "orderDelivered");
        log.info(markers, "making order delivered by shipper: {}", UUID.randomUUID());
        //todo: get shipper id by token

        OrderEntity orderEntity = orderService.getOrderById(orderId);
        //todo: get shipper id by token
        UUID shipperId = UUID.fromString("6fdb2bac-4709-4957-a2b2-80d9b3811e2f");
        ShipperEntity shipperEntity = getShipperById(shipperId);

        if(!shipperEntity.getId().equals(orderEntity.getShipperId())) {
            log.info(markers, "shipper can not deliver order which is not assigned to them");
            throw ServiceException.badRequest(Errors.SHIPPER_NOT_ASSIGNED_TO_ORDER,
                    Errors.errorMap.get(Errors.SHIPPER_NOT_ASSIGNED_TO_ORDER));
        }

        if(!OrderStatusType.PICKED_UP.equals(orderEntity.getOrderStatus())) {
            log.info(markers, "order can not be delivered when it is not picked up");
            throw ServiceException.badRequest(Errors.ORDER_NOT_PICKED_UP,
                    Errors.errorMap.get(Errors.ORDER_NOT_PICKED_UP));
        }

        orderEntity = orderService.updateOrderStatus(orderId, OrderStatusType.DELIVERED);
        updateShipperAvailability(shipperId, Boolean.TRUE);
        paymentService.processPayment(orderEntity);
        return OrderDto.fromEntity(orderEntity);
    }

    @Override
    public OrderRequestDto rateCustomer(UUID orderRequestId, Integer rating) {
        return null;
    }

    @Override
    public ShipperEntity getShipperById(UUID id) {
        LogstashMarker markers = append("method", "getShipperById");
        log.info(markers, "getting shipper with id: {}", id);

        ShipperEntity entity = shipperRepository.findById(id).orElse(null);
        if(Objects.isNull(entity)) {
            log.info(markers, "no shipper found with id: {}", id);
            throw ServiceException.internalError(Errors.SHIPPER_NOT_FOUND,
                    Errors.errorMap.get(Errors.SHIPPER_NOT_FOUND));
        }
        return entity;
    }

    @Override
    public List<OrderRequestDto> getAllOrder() {
        return List.of();
    }

    @Override
    public List<ShipperEntity> getTenNearestShipper(Point location) {
        LogstashMarker markers = append("method", "getTenNearestShipper");
        log.info(markers, "getting ten nearest shipper to location: {}", location);
        List<ShipperEntity> shipperEntityList = shipperRepository.findNearest10Shippers(location);

        if(CollectionUtils.isEmpty(shipperEntityList)) {
            log.info(markers, "no shipper found");
            throw ServiceException.internalError(Errors.NO_SHIPPER_AVAILABLE,
                    Errors.errorMap.get(Errors.NO_SHIPPER_AVAILABLE));
        }
        return shipperEntityList;
    }

    @Override
    public List<ShipperEntity> getTenNearbyHighestRatedShipper(Point location) {
        LogstashMarker markers = append("method", "getTenNearbyHighestRatedShipper");
        log.info(markers, "getting ten highest rated shipper which are nearby: {}", location);
        List<ShipperEntity> shipperEntityList = shipperRepository.findTenNearbyTopRatedShipper(location);
        if(CollectionUtils.isEmpty(shipperEntityList)) {
            log.info(markers, "no shipper found");
            throw ServiceException.internalError(Errors.NO_SHIPPER_AVAILABLE,
                    Errors.errorMap.get(Errors.NO_SHIPPER_AVAILABLE));
        }
        return shipperEntityList;
    }

    @Transactional
    private ShipperEntity updateShipperAvailability(UUID id, Boolean available) {
        LogstashMarker markers = append("method", "updateShipperAvailability");
        log.info(markers, "updating shipper availability of id: {} to: {}", id, available);

        ShipperEntity entity = getShipperById(id);
        entity.setAvailable(available);
        shipperRepository.save(entity);
        return entity;
    }

    private void createOrder(ShipperEntity shipperEntity, OrderRequestEntity orderRequestEntity) {
        LogstashMarker markers = append("method", "createOrder");
        log.info(markers, "creating order for order request: {}, with shipper: {}",
                orderRequestEntity.getId(), shipperEntity.getId());

        if(!OrderRequestStatusType.CONFIRMED.equals(orderRequestEntity.getOrderRequestStatusType())) {
            log.info(markers, "can not create order as order request status is not confirmed");
            return;
        }
        try{
            log.info(markers, "publishing accept order event for order request: {}, with shipper: {}",
                    orderRequestEntity.getId(), shipperEntity.getId());
            AcceptOrderEvent acceptOrderEvent = new AcceptOrderEvent();
            acceptOrderEvent.setOrderRequestDto(OrderRequestDto.fromEntity(orderRequestEntity));
            acceptOrderEvent.setShipperDto(ShipperDto.fromEntity(shipperEntity));
            applicationEventPublisher.publishEvent(acceptOrderEvent);
        } catch (Exception ex) {
            log.info(markers, "could not publish accept order event : {}", ExceptionUtils.getStackTrace(ex));
        }
    }

    private OrderEntity getAcceptedOrderForShipper(List<OrderEntity> orderEntityList) {
        LogstashMarker markers = append("method", "getOrderForShipper");
        log.info(markers, "getting order for the shipper with id: {}", UUID.randomUUID());

        //get shipper id from the token
        UUID shipperId = UUID.randomUUID();
        OrderEntity orderEntity = null;
        for(OrderEntity entity : orderEntityList) {
            if(entity.getOrderStatus().equals(OrderStatusType.ACCEPTED) && entity.getShipperId().equals(shipperId)) {
                orderEntity = entity;
            }
        }
        if(Objects.isNull(orderEntity)) {
            log.info(markers, "no accepted order found associated with shipper");
            throw ServiceException.badRequest(Errors.ORDER_NOT_FOUND,
                    Errors.errorMap.get(Errors.ORDER_NOT_FOUND));
        }
        return orderEntity;
    }
}
