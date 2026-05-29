package com.pgs.ecommerce.payment.service;

import com.pgs.ecommerce.payment.dto.PaymentRequest;

public interface PaymentService {

	Integer createPayment(PaymentRequest request);

}
