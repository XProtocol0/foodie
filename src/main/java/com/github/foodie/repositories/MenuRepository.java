package com.github.foodie.repositories;

import com.github.foodie.entities.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, UUID> {
    @Query("SELECT m from MenuEntity m where m.restaurantId = ?1")
    List<MenuEntity> findAllByRestaurantId(UUID restaurantId);
}
