package com.github.foodie.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "shipper")
public class ShipperEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccountEntity userAccountEntity;

    @Column(name = "user_id", updatable = false, insertable = false)
    private Long userId;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "location", columnDefinition = "Geometry(Point, 4326)")
    private Point location;
}
