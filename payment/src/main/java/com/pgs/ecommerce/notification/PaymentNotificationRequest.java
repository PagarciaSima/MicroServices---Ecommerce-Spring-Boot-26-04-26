package com.pgs.ecommerce.notification;

import java.math.BigDecimal;

import com.pgs.ecommerce.payment.entity.PaymentMethod;

public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstname,
        String customerLastname,
        String customerEmail
) {
}
