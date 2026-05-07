package com.pgs.ecommerce.orderline.repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgs.ecommerce.orderline.entity.OrderLine;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {

    List<OrderLine> findAllByOrderId(Integer orderId);
}
