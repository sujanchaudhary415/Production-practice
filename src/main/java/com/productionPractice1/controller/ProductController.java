package com.productionPractice1.controller;



import com.productionPractice1.dto.request.ProductRequest;
import com.productionPractice1.dto.response.ProductResponse;
import com.productionPractice1.service.ProductServiceImpl;
import com.productionPractice1.wrapper.ApiResponse;
import com.productionPractice1.wrapper.PagedResponse;
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

    @GetMapping("/public/products")
    public ResponseEntity<ApiResponse<PagedResponse<ProductResponse>>> getAllProduct(@RequestParam (defaultValue = "0") int pageNumber,
                                                                                    @RequestParam (defaultValue ="5" ) int pageSize,
                                                                                    @RequestParam (defaultValue ="productId" ) String sortBy,
                                                                                    @RequestParam (defaultValue = "asc")String sortDir
                                                                      )
    {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(productServiceImpl.getAllProduct(pageNumber, pageSize, sortBy, sortDir),"All product fetched Successfully"));
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ApiResponse<PagedResponse<ProductResponse>>> getProductsByCategory(@PathVariable Long categoryId,@RequestParam (defaultValue = "0") int pageNumber,
                                                                                            @RequestParam (defaultValue ="5" ) int pageSize,
                                                                                            @RequestParam (defaultValue ="productId" ) String sortBy,
                                                                                            @RequestParam (defaultValue = "asc")String sortDir)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(productServiceImpl.getProductsByCategory(categoryId,pageNumber,pageSize,sortBy,sortDir),"Products by category fetched Successfully"));
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable Long productId,@RequestBody ProductRequest request)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(productServiceImpl.updateProduct(productId,request),"Product updated Successfully"));
    }
}
