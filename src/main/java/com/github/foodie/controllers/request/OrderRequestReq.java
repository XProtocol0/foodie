package com.github.foodie.controllers.request;

import com.github.foodie.constants.PaymentType;
import com.github.foodie.dtos.PointDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.github.foodie.validation.EnumValue;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestReq {
    @NotNull(message = "customerId not provided")
    private UUID customerId;
    @NotNull(message = "restaurantId not provided")
    private UUID restaurantId;
    private List<UUID> items;
    @EnumValue(enumClass = PaymentType.class, message = "Invalid payment type")
    @NotNull(message = "payment can not be null")
    private String paymentType;
    @NotNull(message = "restaurantLocation not provided")
    private @Valid PointDto restaurantLocation;
    @NotNull(message = "deliveryLocation not provided")
    private @Valid PointDto deliveryLocation;

}
