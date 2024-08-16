package com.github.foodie.dtos;

import com.github.foodie.constants.PaymentStatusType;
import com.github.foodie.constants.PaymentType;
import com.github.foodie.entities.PaymentEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class PaymentDto {
    private UUID id;
    private PaymentType paymentType;
    private UUID orderId;
    private BigDecimal amount;
    private PaymentStatusType paymentStatus;
    private Instant paymentTime;
    private Instant createdOn;
    private Instant updatedOn;

    public static PaymentDto fromEntity(PaymentEntity entity) {
        PaymentDto dto = new PaymentDto();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setOrderId(entity.getOrderId());
        dto.setPaymentTime(entity.getPaymentTime());
        dto.setPaymentStatus(entity.getPaymentStatus());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedOn(entity.getUpdatedOn());
        return dto;
    }
}
