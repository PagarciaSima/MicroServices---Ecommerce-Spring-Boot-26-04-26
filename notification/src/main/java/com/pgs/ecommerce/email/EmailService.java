package com.pgs.ecommerce.email;

import java.math.BigDecimal;
import java.util.List;

import com.pgs.ecommerce.kafka.order.Product;

import jakarta.mail.MessagingException;

public interface EmailService {

	void sendPaymentSuccessEmail(String destinationEmail, String customerName, BigDecimal amount, String orderReference)
			throws MessagingException;

	void sendOrderConfirmationEmail(String destinationEmail, String customerName, BigDecimal amount,
			String orderReference, List<Product> products) throws MessagingException;

}
