package com.productionPractice1.controller;


import com.productionPractice1.dto.request.CategoryRequest;
import com.productionPractice1.dto.request.ProductRequest;
import com.productionPractice1.dto.response.ProductResponse;
import com.productionPractice1.service.ProductServiceImpl;
import com.productionPractice1.wrapper.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    public ProductController(ProductServiceImpl productServiceImpl)
    {
        this.productServiceImpl=productServiceImpl;
    }

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request, @PathVariable Long categoryId)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(productServiceImpl.createProduct(request,categoryId),"Product Created"));
    }

}
