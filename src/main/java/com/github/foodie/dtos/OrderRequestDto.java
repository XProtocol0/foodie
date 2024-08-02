package com.github.foodie.dtos;

import com.github.foodie.constants.OrderRequestStatusType;
import com.github.foodie.constants.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private Point restaurantLocation;
    private Point deliveryLocation;
    private CustomerDto customer;
    private UUID restaurantId;
    private List<String> items;
    private PaymentType paymentType;
    private OrderRequestStatusType orderRequestStatusType;
    private Instant createdOn;
    private Instant updatedOn;
}
