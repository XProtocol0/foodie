package com.github.foodie.repositories;

import com.github.foodie.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, UUID> {
    WalletEntity findByUserId(Long userId);
}
