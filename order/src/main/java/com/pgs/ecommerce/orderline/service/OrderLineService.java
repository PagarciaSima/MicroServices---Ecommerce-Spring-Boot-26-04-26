package com.pgs.ecommerce.orderline.service;

import java.util.List;

import com.pgs.ecommerce.orderline.dto.OrderLineRequest;
import com.pgs.ecommerce.orderline.dto.OrderLineResponse;

public interface OrderLineService {

	Integer saveOrderLine(OrderLineRequest request);

	List<OrderLineResponse> findAllByOrderId(Integer orderId);

}
