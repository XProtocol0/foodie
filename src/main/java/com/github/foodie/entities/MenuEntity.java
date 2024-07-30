package com.github.foodie.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "menu")
public class MenuEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurantEntity;

    @Column(name = "restaurant_id", updatable = false, insertable = false)
    private UUID restaurantId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "vegetarian")
    private Boolean vegetarian;

    @Column(name = "ingredients")
    private String ingredients;

}
