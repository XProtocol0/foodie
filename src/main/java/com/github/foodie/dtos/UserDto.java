package com.github.foodie.dtos;

import com.github.foodie.constants.Role;
import com.github.foodie.entities.UserAccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<Role> roles;
    private Instant createdOn;
    private Instant updatedOn;

    public static UserDto fromEntity(UserAccountEntity entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setRoles(entity.getRoles());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedOn(entity.getUpdatedOn());
        return dto;
    }

    public static UserAccountEntity toEntity(UserDto dto) {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRoles(dto.getRoles());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setUpdatedOn(dto.getUpdatedOn());
        return entity;
    }
}
