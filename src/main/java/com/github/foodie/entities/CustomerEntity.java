package com.github.foodie.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserAccountEntity userAccountEntity;

    @Column(name = "user_id", updatable = false, insertable = false)
    private Long userId;

    @Column(name = "rating")
    private BigDecimal rating;

    // add address field as well
}
