package com.pgs.ecommerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgs.ecommerce.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
