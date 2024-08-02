package com.github.foodie.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserAccountEntity userAccountEntity;

    @Column(name = "user_id", insertable=false, updatable=false)
    private Long userId;

    @Column(name = "address")
    private String address;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "opening_hour")
    private Integer openingHour;

    @Column(name = "closing_hour")
    private Integer closingHour;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "location", columnDefinition = "Geometry(Point, 4326)")
    private Point location;

}
