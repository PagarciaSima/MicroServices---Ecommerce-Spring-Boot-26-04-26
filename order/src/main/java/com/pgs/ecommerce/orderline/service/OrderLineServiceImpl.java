package com.pgs.ecommerce.orderline.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pgs.ecommerce.orderline.dto.OrderLineRequest;
import com.pgs.ecommerce.orderline.dto.OrderLineResponse;
import com.pgs.ecommerce.orderline.entity.OrderLine;
import com.pgs.ecommerce.orderline.mapper.OrderLineMapper;
import com.pgs.ecommerce.orderline.repositoy.OrderLineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderLineServiceImpl implements OrderLineService{

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    @Override
    public Integer saveOrderLine(OrderLineRequest request) {
        OrderLine order = mapper.toOrderLine(request);
        return repository.save(order).getId();
    }

    @Override
    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}