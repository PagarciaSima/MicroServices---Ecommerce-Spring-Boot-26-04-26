package com.pgs.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.pgs.ecommerce.dto.CustomerRequest;
import com.pgs.ecommerce.dto.CustomerResponse;
import com.pgs.ecommerce.entity.Customer;
import com.pgs.ecommerce.exception.CustomerNotFoundException;
import com.pgs.ecommerce.mapper.CustomerMapper;
import com.pgs.ecommerce.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository repository;
	private final CustomerMapper mapper;

	@Override
	public String createCustomer(CustomerRequest request) {
		var customer = this.repository.save(mapper.toCustomer(request));
		return customer.getId();
	}

	@Override
	public void updateCustomer(CustomerRequest request) {
		var customer = this.repository.findById(request.id()).
				orElseThrow(() -> new CustomerNotFoundException(
						String.format("Cannot update customer:: No customer found with the provided ID: %s", request.id()))
				);
		// Make sure we don't override existing values with null or blank values from the request
		mergeCustomer(customer, request);
		this.repository.save(customer);
	}

	@Override
	public List<CustomerResponse> findAllCustomers() {
		return this.repository.findAll().stream().map(this.mapper::fromCustomer).collect(Collectors.toList());
	}

	@Override
	public CustomerResponse findById(String id) {
		return this.repository.findById(id).map(mapper::fromCustomer).orElseThrow(
				() -> new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", id)));
	}

	@Override
	public boolean existsById(String id) {
		return this.repository.findById(id).isPresent();
	}

	@Override
	public void deleteCustomer(String id) {
		this.repository.deleteById(id);
	}

	private void mergeCustomer(Customer customer, CustomerRequest request) {
		if (StringUtils.isNotBlank(request.firstname())) {
			customer.setFirstname(request.firstname());
		}
		if (StringUtils.isNotBlank(request.lastname())) {
			customer.setLastname(request.lastname());
		}
		if (StringUtils.isNotBlank(request.email())) {
			customer.setEmail(request.email());
		}
		if (request.address() != null) {
			customer.setAddress(request.address());
		}
	}
}
