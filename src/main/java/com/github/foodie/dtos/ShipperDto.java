package com.github.foodie.dtos;

import com.github.foodie.entities.ShipperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipperDto {
    private UUID id;
    private UserDto user;
    private BigDecimal rating;
    private Boolean available;
    private Point location;

    public static ShipperDto fromEntity(ShipperEntity entity) {
        ShipperDto dto = new ShipperDto();
        dto.setId(entity.getId());
        dto.setUser(UserDto.fromEntity(entity.getUserAccountEntity()));
        dto.setRating(entity.getRating());
        dto.setAvailable(entity.getAvailable());
        dto.setLocation(entity.getLocation());
        return dto;
    }

    public static ShipperEntity toEntity(ShipperDto dto) {
        ShipperEntity entity = new ShipperEntity();
        entity.setUserAccountEntity(UserDto.toEntity(dto.getUser()));
        entity.setUserId(dto.getUser().getId());
        entity.setAvailable(dto.getAvailable());
        entity.setRating(dto.getRating());
        entity.setLocation(dto.getLocation());
        return entity;
    }
}
