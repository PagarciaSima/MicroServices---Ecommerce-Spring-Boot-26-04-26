package com.pgs.ecommerce.order.service;

import java.util.List;

import com.pgs.ecommerce.order.dto.OrderRequest;
import com.pgs.ecommerce.order.dto.OrderResponse;

public interface OrderService {

	Integer createOrder(OrderRequest request);

	List<OrderResponse> findAllOrders();

	OrderResponse findById(Integer id);

}
