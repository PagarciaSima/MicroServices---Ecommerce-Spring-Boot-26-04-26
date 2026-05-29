package com.pgs.ecommerce.payment.dto;

import java.math.BigDecimal;

import com.pgs.ecommerce.payment.entity.PaymentMethod;

public record PaymentRequest(Integer id, BigDecimal amount, PaymentMethod paymentMethod, Integer orderId,
		String orderReference, Customer customer) {
}
