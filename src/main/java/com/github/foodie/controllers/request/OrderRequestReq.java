package com.github.foodie.controllers.request;

import com.github.foodie.constants.PaymentType;
import com.github.foodie.dtos.PointDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestReq {
    private UUID customerId;
    private UUID restaurantId;
    private List<UUID> items;
    private PaymentType paymentType;
    private PointDto restaurantLocation;
    private PointDto deliveryLocation;

}
