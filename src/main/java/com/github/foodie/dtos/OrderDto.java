package com.github.foodie.dtos;

import com.github.foodie.constants.OrderStatusType;
import com.github.foodie.entities.OrderEntity;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID id;
    private UUID orderRequestId;
    private OrderStatusType orderStatus;
    private Instant createdOn;
    private Instant updatedOn;

    public static OrderDto fromEntity(OrderEntity entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setOrderRequestId(entity.getOrderRequestId());
        dto.setOrderStatus(entity.getOrderStatus());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedOn(entity.getUpdatedOn());
        return dto;
    }
}
