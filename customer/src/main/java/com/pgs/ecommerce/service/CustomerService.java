package com.pgs.ecommerce.service;

import java.util.List;

import com.pgs.ecommerce.dto.CustomerRequest;
import com.pgs.ecommerce.dto.CustomerResponse;

public interface CustomerService {

	String createCustomer(CustomerRequest request);

	void updateCustomer(CustomerRequest request);

	List<CustomerResponse> findAllCustomers();

	CustomerResponse findById(String id);

	boolean existsById(String id);

	void deleteCustomer(String id);

}
