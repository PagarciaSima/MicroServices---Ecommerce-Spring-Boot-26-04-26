package com.pgs.ecommerce.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgs.ecommerce.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
