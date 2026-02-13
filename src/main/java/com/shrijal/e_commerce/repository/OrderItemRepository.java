package com.shrijal.e_commerce.repository;

import com.shrijal.e_commerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Find all order items by order id
    // Used when viewing order details
    List<OrderItem> findByOrderId(Long orderId);

    // Find order items by product id
    List<OrderItem> findByProductId(Long productId);
}
