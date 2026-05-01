package com.pgs.ecommerce.service;

import java.util.List;

import com.pgs.ecommerce.dto.ProductPurchaseRequest;
import com.pgs.ecommerce.dto.ProductPurchaseResponse;
import com.pgs.ecommerce.dto.ProductRequest;
import com.pgs.ecommerce.dto.ProductResponse;

public interface ProductService {

	Integer createProduct(ProductRequest request);

	ProductResponse findById(Integer id);

	List<ProductResponse> findAll();

	List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request);

}
