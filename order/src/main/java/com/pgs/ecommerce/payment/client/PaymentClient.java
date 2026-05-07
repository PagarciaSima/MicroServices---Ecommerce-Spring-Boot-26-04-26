package com.pgs.ecommerce.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pgs.ecommerce.payment.dto.PaymentRequest;

@FeignClient(name = "product-service", url = "${application.config.payment-url}")
public interface PaymentClient {

	@PostMapping
	Integer requestOrderPayment(@RequestBody PaymentRequest request);
}
