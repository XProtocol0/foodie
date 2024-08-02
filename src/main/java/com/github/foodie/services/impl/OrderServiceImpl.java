package com.github.foodie.services.impl;

import com.github.foodie.constants.OrderRequestStatusType;
import com.github.foodie.controllers.request.OrderRequestReq;
import com.github.foodie.entities.CustomerEntity;
import com.github.foodie.entities.OrderRequestEntity;
import com.github.foodie.entities.RestaurantEntity;
import com.github.foodie.repositories.OrderRequestRepository;
import com.github.foodie.services.CustomerService;
import com.github.foodie.services.OrderService;
import com.github.foodie.services.RestaurantService;
import com.github.foodie.strategies.DeliveryChargeCalculationStrategy;
import com.github.foodie.utils.GeometryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private OrderRequestRepository orderRequestRepository;
    @Autowired
    private DeliveryChargeCalculationStrategy deliveryChargeCalculationStrategy;

    @Override
    public void requestOrder(OrderRequestReq orderRequestReq) {
        OrderRequestEntity orderRequestEntity = getOrderRequestEntityFromReq(orderRequestReq);
        BigDecimal charge = deliveryChargeCalculationStrategy.calculateCharge(
                orderRequestEntity.getRestaurantLocation(), orderRequestEntity.getDeliveryLocation());



        orderRequestRepository.save(orderRequestEntity);
    }

    private OrderRequestEntity getOrderRequestEntityFromReq(OrderRequestReq orderRequestReq) {
        OrderRequestEntity orderRequestEntity = new OrderRequestEntity();
        CustomerEntity customerEntity = customerService.getCustomer(orderRequestReq.getCustomerId());
        RestaurantEntity restaurantEntity = restaurantService.getRestaurant(orderRequestReq.getRestaurantId());
        orderRequestEntity.setCustomerEntity(customerEntity);
        orderRequestEntity.setCustomerId(customerEntity.getId());
        orderRequestEntity.setRestaurantId(restaurantEntity.getId());
        orderRequestEntity.setRestaurantLocation(
                GeometryUtil.createPoint(orderRequestReq.getRestaurantLocation()));
        orderRequestEntity.setDeliveryLocation(
                GeometryUtil.createPoint(orderRequestReq.getDeliveryLocation()));
        orderRequestEntity.setPaymentType(orderRequestReq.getPaymentType());
        orderRequestEntity.setOrderRequestStatusType(OrderRequestStatusType.PENDING);
        orderRequestEntity.setItems(orderRequestReq.getItems());

        return orderRequestEntity;
    }
}
