package com.pgs.ecommerce.payment.mapper;

import org.springframework.stereotype.Service;

import com.pgs.ecommerce.payment.dto.PaymentRequest;
import com.pgs.ecommerce.payment.entity.Payment;

@Service
public class PaymentMapper {

  public Payment toPayment(PaymentRequest request) {
    if (request == null) {
      return null;
    }
    return Payment.builder()
        .id(request.id())
        .paymentMethod(request.paymentMethod())
        .amount(request.amount())
        .orderId(request.orderId())
        .build();
  }
}