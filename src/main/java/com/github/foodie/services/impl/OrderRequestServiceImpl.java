package com.github.foodie.services.impl;

import com.github.foodie.constants.*;
import com.github.foodie.controllers.request.OrderRequestReq;
import com.github.foodie.entities.*;
import com.github.foodie.exceptions.Errors;
import com.github.foodie.exceptions.ServiceException;
import com.github.foodie.repositories.OrderRepository;
import com.github.foodie.repositories.OrderRequestRepository;
import com.github.foodie.services.*;
import com.github.foodie.utils.GeometryUtil;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static net.logstash.logback.marker.Markers.append;

@Service
@Slf4j
public class OrderRequestServiceImpl implements OrderRequestService {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private OrderRequestRepository orderRequestRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public void requestOrder(OrderRequestReq orderRequestReq) {
        LogstashMarker markers = append("method", "requestOrder");
        log.info(markers, "requesting order");

        CustomerEntity customerEntity = customerService.getCustomer(orderRequestReq.getCustomerId());
        RestaurantEntity restaurantEntity = restaurantService.getRestaurant(orderRequestReq.getRestaurantId());
        OrderRequestEntity orderRequestEntity =
                getOrderRequestEntityFromReq(orderRequestReq, customerEntity, restaurantEntity);
//
//        BigDecimal deliveryCharge = strategyManager.deliveryChargeCalculationStrategy().calculateCharge(
//                orderRequestEntity.getRestaurantLocation(), orderRequestEntity.getDeliveryLocation());
//
        //TODO: create requestOrder event & send notification to all shippers
//
//
//        OrderEntity orderEntity = createOrderFromRequest(orderRequestEntity, shipperEntityList, deliveryCharge);
//
//        paymentService.createPaymentEntity(orderEntity, orderRequestReq, deliveryCharge);

        orderRequestRepository.save(orderRequestEntity);
//        orderRepository.save(orderEntity);

    }

    @Override
    public OrderRequestEntity getRequestOrderById(UUID orderRequestId) {
        LogstashMarker markers = append("method", "getRequestOrderById");
        log.info(markers, "getting request order by id: {}", orderRequestId);

        OrderRequestEntity entity = orderRequestRepository.findById(orderRequestId).orElse(null);
        if(Objects.isNull(entity)) {
            log.info(markers, "order request does not exists for id: {}", orderRequestId);
            throw ServiceException.internalError(Errors.ORDER_REQUEST_NOT_FOUND,
                    Errors.errorMap.get(Errors.ORDER_REQUEST_NOT_FOUND));
        }
        return entity;
    }


    @Override
    @Transactional
    public OrderRequestEntity updateOrderRequestStatus(UUID orderRequestId, OrderRequestStatusType orderRequestStatusType) {
        LogstashMarker markers = append("method", "updateOrderRequestStatus");
        log.info(markers, "updating order request status");

        OrderRequestEntity entity = getRequestOrderById(orderRequestId);
        entity.setOrderRequestStatusType(orderRequestStatusType);
        orderRequestRepository.save(entity);
        return entity;
    }

    private OrderEntity createOrderFromRequest(OrderRequestEntity orderRequestEntity,
                                               List<ShipperEntity> shipperEntityList,
                                               BigDecimal deliveryCharge) {
        LogstashMarker markers = append("method", "createOrderFromRequest");
        log.info(markers, "creating order from request");

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setOrderRequestEntity(orderRequestEntity);
        orderEntity.setOrderRequestId(orderRequestEntity.getId());
        orderEntity.setOrderStatus(OrderStatusType.ACCEPTED);
        orderEntity.setShipperId(shipperEntityList.get(0).getId());
        return orderEntity;
    }

    private OrderRequestEntity getOrderRequestEntityFromReq(OrderRequestReq orderRequestReq,
                                                            CustomerEntity customerEntity,
                                                            RestaurantEntity restaurantEntity) {
        LogstashMarker markers = append("method", "getOrderRequestEntityFromReq");
        log.info(markers, "getting order request entity from request");
        OrderRequestEntity orderRequestEntity = new OrderRequestEntity();
        orderRequestEntity.setCustomerEntity(customerEntity);
        orderRequestEntity.setCustomerId(customerEntity.getId());
        orderRequestEntity.setRestaurantId(restaurantEntity.getId());
        orderRequestEntity.setRestaurantLocation(
                GeometryUtil.createPoint(orderRequestReq.getRestaurantLocation()));
        orderRequestEntity.setDeliveryLocation(
                GeometryUtil.createPoint(orderRequestReq.getDeliveryLocation()));
        orderRequestEntity.setPaymentType(PaymentType.valueOf(orderRequestReq.getPaymentType()));
        orderRequestEntity.setOrderRequestStatusType(OrderRequestStatusType.PENDING);
        orderRequestEntity.setItems(orderRequestReq.getItems());

        return orderRequestEntity;
    }
}
