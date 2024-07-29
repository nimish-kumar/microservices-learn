package com.microservices.ecommerce.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ProductRepository repository;
    private ProductMapper mapper;

    public Long createProduct(ProductRequest request) {
        var product = mapper.toProduct(request);
        return null;
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        return null;
    }

    public ProductResponse findById(Long productId) {
        return null;
    }


    public List<ProductResponse> findAll() {
        return null;
    }
}
