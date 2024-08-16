package com.github.foodie.dtos;

import com.github.foodie.constants.OrderRequestStatusType;
import com.github.foodie.constants.PaymentType;
import com.github.foodie.entities.OrderEntity;
import com.github.foodie.entities.OrderRequestEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private UUID id;
    private Point restaurantLocation;
    private Point deliveryLocation;
    private UUID customerId;
    private UUID restaurantId;
    private List<UUID> items;
    private PaymentType paymentType;
    private OrderRequestStatusType orderRequestStatus;
    private Instant createdOn;
    private Instant updatedOn;

    public static OrderRequestDto fromEntity(OrderRequestEntity entity) {
        OrderRequestDto dto = new OrderRequestDto();
        dto.setId(entity.getId());
        dto.setCustomerId(entity.getCustomerId());
        //dto.setDeliveryLocation(entity.getDeliveryLocation());
        //dto.setRestaurantLocation(entity.getRestaurantLocation());
      //  dto.setItems(entity.getItems());
        dto.setPaymentType(entity.getPaymentType());
        dto.setOrderRequestStatus(entity.getOrderRequestStatusType());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedOn(entity.getUpdatedOn());
        return dto;
    }

    public static OrderRequestEntity toEntity(OrderRequestDto dto) {
        OrderRequestEntity entity = new OrderRequestEntity();
        entity.setId(dto.getId());
        entity.setCustomerId(dto.getCustomerId());
        entity.setRestaurantLocation(dto.getRestaurantLocation());
        entity.setDeliveryLocation(dto.getDeliveryLocation());
        entity.setRestaurantId(dto.getRestaurantId());
        entity.setPaymentType(dto.getPaymentType());
        entity.setItems(dto.getItems());
        entity.setOrderRequestStatusType(dto.getOrderRequestStatus());
        return entity;
    }
}
