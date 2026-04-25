package com.productionPractice1.service;

import com.productionPractice1.dto.request.ProductRequest;
import com.productionPractice1.dto.response.ProductResponse;
import com.productionPractice1.wrapper.PagedResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request,Long categoryId);
    PagedResponse<ProductResponse> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir);
    PagedResponse<ProductResponse> getProductsByCategory(Long categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);
    ProductResponse updateProduct(Long productId,ProductRequest request);
}
