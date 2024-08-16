package com.github.foodie.repositories;

import com.github.foodie.constants.OrderStatusType;
import com.github.foodie.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByOrderRequestId(UUID orderRequestId);

    @Query(value = "SELECT o.* from orders o WHERE o.shipper_id= :shipperId AND " +
            "o.order_status IN (:orderStatusTypes)",
            nativeQuery = true)
    OrderEntity findByShipperIdAndOrderStatus(UUID shipperId, List<String> orderStatusTypes);
}
