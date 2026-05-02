package com.pgs.ecommerce.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pgs.ecommerce.dto.ProductPurchaseRequest;
import com.pgs.ecommerce.dto.ProductPurchaseResponse;
import com.pgs.ecommerce.dto.ProductRequest;
import com.pgs.ecommerce.dto.ProductResponse;
import com.pgs.ecommerce.entity.product.Product;
import com.pgs.ecommerce.exception.ProductPurchaseException;
import com.pgs.ecommerce.mapper.ProductMapper;
import com.pgs.ecommerce.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl	implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public Integer createProduct(
            ProductRequest request
    ) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    @Override
    public ProductResponse findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + id));
    }

    @Override
    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(
            List<ProductPurchaseRequest> request
    ) {
        List<Integer> productIds = request
            .stream()
            .map(ProductPurchaseRequest::productId)
            .toList();
        
        List<Product> storedProducts = repository.findAllByIdInOrderById(productIds);
        
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }
        
        List<ProductPurchaseRequest> sortedRequest = request
            .stream()
            .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
            .toList();
        
        ArrayList<ProductPurchaseResponse> purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        handleProductPurchase(storedProducts, sortedRequest, purchasedProducts);
        return purchasedProducts;
    }

	private void handleProductPurchase(List<Product> storedProducts, List<ProductPurchaseRequest> sortedRequest,
			ArrayList<ProductPurchaseResponse> purchasedProducts) {
		for (int i = 0; i < storedProducts.size(); i++) {
            Product product = storedProducts.get(i);
            ProductPurchaseRequest productRequest = sortedRequest.get(i);
            checkEnoughProductStock(product, productRequest);
            updateQuantityAfterPurchase(product, productRequest);
            purchasedProducts.add(mapper.toproductPurchaseResponse(product, productRequest.quantity()));
        }
	}

	private void checkEnoughProductStock(Product product, ProductPurchaseRequest productRequest) {
		if (product.getAvailableQuantity() < productRequest.quantity()) {
			throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productRequest.productId());
		}
	}
	
	private void updateQuantityAfterPurchase(Product product, ProductPurchaseRequest productRequest) {
		double newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
		product.setAvailableQuantity(newAvailableQuantity);
		repository.save(product);
	}

}
