package com.github.foodie.dtos;

import com.github.foodie.entities.MenuEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
    private String itemName;
    private BigDecimal price;
    private Boolean vegetarian;
    private String ingredients;


    public static MenuDto fromEntity(MenuEntity menuEntity) {
        MenuDto menuDto = new MenuDto();
        menuDto.setItemName(menuEntity.getItemName());
        menuDto.setPrice(menuEntity.getPrice());
        menuDto.setVegetarian(menuEntity.getVegetarian());
        menuDto.setIngredients(menuEntity.getIngredients());
        return menuDto;
    }
}
