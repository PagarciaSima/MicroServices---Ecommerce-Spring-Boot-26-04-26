package com.pgs.ecommerce.order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pgs.ecommerce.customer.client.CustomerClient;
import com.pgs.ecommerce.customer.dto.CustomerResponse;
import com.pgs.ecommerce.exception.BusinessException;
import com.pgs.ecommerce.kafka.OrderConfirmation;
import com.pgs.ecommerce.kafka.OrderProducer;
import com.pgs.ecommerce.order.dto.OrderRequest;
import com.pgs.ecommerce.order.dto.OrderResponse;
import com.pgs.ecommerce.order.entity.Order;
import com.pgs.ecommerce.order.mapper.OrderMapper;
import com.pgs.ecommerce.order.repository.OrderRepository;
import com.pgs.ecommerce.orderline.dto.OrderLineRequest;
import com.pgs.ecommerce.orderline.service.OrderLineService;
import com.pgs.ecommerce.payment.client.PaymentClient;
import com.pgs.ecommerce.payment.dto.PaymentRequest;
import com.pgs.ecommerce.product.client.ProductClient;
import com.pgs.ecommerce.product.dto.PurchaseRequest;
import com.pgs.ecommerce.product.dto.PurchaseResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Transactional
    @Override
    public Integer createOrder(OrderRequest request) {
    	CustomerResponse customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

    	List<PurchaseResponse> purchasedProducts = productClient.purchaseProducts(request.products());

        Order order = this.repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        
        PaymentRequest paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    @Override
    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}
