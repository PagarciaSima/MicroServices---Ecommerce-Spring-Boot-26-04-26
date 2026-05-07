package com.pgs.ecommerce.payment.dto;

import java.math.BigDecimal;

import com.pgs.ecommerce.customer.dto.CustomerResponse;
import com.pgs.ecommerce.order.entity.PaymentMethod;

public record PaymentRequest(
	    BigDecimal amount,
	    PaymentMethod paymentMethod,
	    Integer orderId,
	    String orderReference,
	    CustomerResponse customer
	) {
	}
