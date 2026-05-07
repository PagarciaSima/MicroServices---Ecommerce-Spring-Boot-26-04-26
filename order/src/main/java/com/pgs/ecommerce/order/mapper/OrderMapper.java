package com.pgs.ecommerce.order.mapper;

import org.springframework.stereotype.Service;

import com.pgs.ecommerce.order.dto.OrderRequest;
import com.pgs.ecommerce.order.dto.OrderResponse;
import com.pgs.ecommerce.order.entity.Order;

@Service
public class OrderMapper {


  public Order toOrder(OrderRequest request) {
    if (request == null) {
      return null;
    }
    return Order.builder()
        .id(request.id())
        .reference(request.reference())
        .paymentMethod(request.paymentMethod())
        .customerId(request.customerId())
        .build();
  }

  public OrderResponse fromOrder(Order order) {
    return new OrderResponse(
        order.getId(),
        order.getReference(),
        order.getTotalAmount(),
        order.getPaymentMethod(),
        order.getCustomerId()
    );
  }
}
