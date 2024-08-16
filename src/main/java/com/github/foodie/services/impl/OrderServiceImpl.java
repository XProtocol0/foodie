package com.github.foodie.services.impl;

import com.github.foodie.constants.OrderStatusType;
import com.github.foodie.dtos.CustomerDto;
import com.github.foodie.dtos.OrderRequestDto;
import com.github.foodie.dtos.ShipperDto;
import com.github.foodie.entities.OrderEntity;
import com.github.foodie.exceptions.Errors;
import com.github.foodie.exceptions.ServiceException;
import com.github.foodie.repositories.OrderRepository;
import com.github.foodie.services.OrderService;
import com.github.foodie.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentService paymentService;

    @Override
    @Transactional
    public OrderEntity createOrder(OrderRequestDto orderRequestDto, ShipperDto shipperDto) {
        LogstashMarker markers = append("method", "createOrder");
        log.info(markers, "creating order");

        OrderEntity entity = new OrderEntity();
        entity.setShipperId(shipperDto.getId());
        entity.setOrderStatus(OrderStatusType.ACCEPTED);
        entity.setOrderRequestEntity(OrderRequestDto.toEntity(orderRequestDto));
        entity.setOrderRequestId(orderRequestDto.getId());
        orderRepository.save(entity);
        return entity;
    }

    @Override
    public Page<CustomerDto> getAllOrdersOfCustomer(UUID customerId, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<ShipperDto> getAllOrdersOfShipper(UUID shipperId, PageRequest pageRequest) {
        return null;
    }

    @Override
    public OrderEntity getOrderById(UUID orderId) {
        LogstashMarker markers = append("method", "getOrderById");
        log.info(markers, "getting order by id: {}", orderId);

        OrderEntity entity = orderRepository.findById(orderId).orElse(null);
        if(Objects.isNull(entity)) {
            log.info(markers, "no order found with id: {}", orderId);
            throw ServiceException.badRequest(Errors.ORDER_NOT_FOUND,
                    Errors.errorMap.get(Errors.ORDER_NOT_FOUND));
        }
        return entity;
    }

    @Override
    public List<OrderEntity> getOrderByOrderRequestId(UUID orderRequestId) {
        LogstashMarker markers = append("method", "getOrderByOrderRequestId");
        log.info(markers, "getting order by order request id: {}", orderRequestId);
        List<OrderEntity> entityList = orderRepository.findByOrderRequestId(orderRequestId);
        if(CollectionUtils.isEmpty(entityList)) {
            log.info(markers, "no order found with order request id: {}", orderRequestId);
            throw ServiceException.badRequest(Errors.ORDER_NOT_FOUND,
                    Errors.errorMap.get(Errors.ORDER_NOT_FOUND));
        }
        return entityList;
    }

    @Override
    @Transactional
    public OrderEntity updateOrderStatus(UUID orderId, OrderStatusType orderStatus) {
        LogstashMarker markers = append("method", "updateOrderStatus");
        log.info(markers, "updating order: {} status to: {}", orderId, orderStatus);

        OrderEntity orderEntity = getOrderById(orderId);
        orderEntity.setOrderStatus(orderStatus);
        orderRepository.save(orderEntity);
        return orderEntity;
    }

    @Override
    public UUID getActiveOrderByShipperId(UUID shipperId) {
        LogstashMarker markers = append("method", "getActiveOrderByShipperId");
        log.info(markers, "getting order by shipper id: {}", shipperId);
        List<String> orderStatusTypes = Arrays.asList(OrderStatusType.ACCEPTED.name(), OrderStatusType.PICKED_UP.name());
        OrderEntity orderEntity = orderRepository.findByShipperIdAndOrderStatus(shipperId, orderStatusTypes);
        if(Objects.isNull(orderEntity)) {
            log.info(markers, "no active order for the shipper: {}", shipperId);
            throw ServiceException.notFound(Errors.ORDER_NOT_FOUND,
                    Errors.errorMap.get(Errors.NO_ORDER_FOUND));
        }
        return orderEntity.getId();
    }

}
