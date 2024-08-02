package com.github.foodie.repositories;

import com.github.foodie.entities.ShipperEntity;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShipperRepository extends JpaRepository<ShipperEntity, UUID> {
    @Query(value = "SELECT s.*, ST_Distance(s.location, :location) AS distance " +
            "FROM shipper s " +
            "WHERE s.available = true AND ST_DWithin(s.location, :location, 10000) " +
            "ORDER BY distance " +
            "LIMIT 10", nativeQuery = true)
    List<ShipperEntity> findNearest10Shippers(Point location);
}
