package com.github.foodie.repositories;

import com.github.foodie.entities.OrderRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRequestRepository extends JpaRepository<OrderRequestEntity, UUID> {
}
