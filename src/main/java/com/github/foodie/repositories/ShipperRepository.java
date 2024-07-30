package com.github.foodie.repositories;

import com.github.foodie.entities.ShipperEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShipperRepository extends JpaRepository<ShipperEntity, UUID> {
}
