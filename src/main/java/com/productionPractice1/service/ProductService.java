package com.productionPractice1.service;

import com.productionPractice1.dto.request.ProductRequest;
import com.productionPractice1.dto.response.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request,Long categoryId);
    ProductResponse getAllProduct();
}
