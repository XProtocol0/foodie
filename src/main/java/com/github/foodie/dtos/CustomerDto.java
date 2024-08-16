package com.github.foodie.dtos;

import com.github.foodie.entities.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private UUID id;
    private UserDto user;
    private BigDecimal rating;

    public static CustomerDto fromEntity(CustomerEntity entity) {
        CustomerDto dto = new CustomerDto();
        dto.setId(entity.getId());
        dto.setUser(UserDto.fromEntity(entity.getUserAccountEntity()));
        dto.setRating(entity.getRating());
        return dto;
    }

    public static CustomerEntity toEntity(CustomerDto dto) {
        CustomerEntity entity = new CustomerEntity();
        entity.setUserAccountEntity(UserDto.toEntity(dto.getUser()));
        entity.setId(dto.getId());
        entity.setRating(dto.getRating());
        entity.setUserId(dto.getUser().getId());
        return entity;
    }
}
