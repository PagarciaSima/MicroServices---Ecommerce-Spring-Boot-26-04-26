package com.pgs.ecommerce.kafka;

import java.math.BigDecimal;
import java.util.List;

import com.pgs.ecommerce.customer.dto.CustomerResponse;
import com.pgs.ecommerce.order.entity.PaymentMethod;
import com.pgs.ecommerce.product.dto.PurchaseResponse;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products

) {
}