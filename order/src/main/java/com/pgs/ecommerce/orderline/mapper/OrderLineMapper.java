package com.pgs.ecommerce.orderline.mapper;

import org.springframework.stereotype.Service;

import com.pgs.ecommerce.order.entity.Order;
import com.pgs.ecommerce.orderline.dto.OrderLineRequest;
import com.pgs.ecommerce.orderline.dto.OrderLineResponse;
import com.pgs.ecommerce.orderline.entity.OrderLine;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
            .id(request.orderId())
            .productId(request.productId())
            .order(
                Order.builder()
                    .id(request.orderId())
                    .build()
            )
            .quantity(request.quantity())
            .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
            orderLine.getId(),
            orderLine.getQuantity()
        );
    }
}
