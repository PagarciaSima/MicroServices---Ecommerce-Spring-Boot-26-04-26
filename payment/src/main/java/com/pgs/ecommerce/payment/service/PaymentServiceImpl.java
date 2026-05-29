package com.pgs.ecommerce.payment.service;

import org.springframework.stereotype.Service;

import com.pgs.ecommerce.notification.NotificationProducer;
import com.pgs.ecommerce.notification.PaymentNotificationRequest;
import com.pgs.ecommerce.payment.dto.PaymentRequest;
import com.pgs.ecommerce.payment.entity.Payment;
import com.pgs.ecommerce.payment.mapper.PaymentMapper;
import com.pgs.ecommerce.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	private final PaymentRepository repository;
	private final PaymentMapper mapper;
	private final NotificationProducer notificationProducer;

	@Override
	public Integer createPayment(PaymentRequest request) {
		Payment payment = this.repository.save(this.mapper.toPayment(request));

		this.notificationProducer.sendNotification(
				new PaymentNotificationRequest(request.orderReference(), request.amount(), request.paymentMethod(),
						request.customer().firstname(), request.customer().lastname(), request.customer().email()));
		return payment.getId();
	}
}
