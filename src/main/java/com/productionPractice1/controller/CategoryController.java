package com.productionPractice1.controller;

import com.productionPractice1.dto.request.CategoryRequest;
import com.productionPractice1.dto.response.CategoryResponse;
import com.productionPractice1.service.CategoryServiceImpl;
import com.productionPractice1.wrapper.ApiResponse;
import com.productionPractice1.wrapper.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    public CategoryController(CategoryServiceImpl categoryServiceImpl)
    {
        this.categoryServiceImpl=categoryServiceImpl;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryRequest request)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(categoryServiceImpl.createCategory(request),"Category Created Successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<CategoryResponse>>> getAllCategory(@RequestParam (defaultValue = "0")int pageNumber,
                                                                                   @RequestParam (defaultValue = "5")int pageSize,
                                                                                   @RequestParam (defaultValue = "categoryId")String sortBy,
                                                                                   @RequestParam (defaultValue = "asc")String sortDir)

    {
        PagedResponse<CategoryResponse>response=categoryServiceImpl.getAllCategory(pageNumber,pageSize,sortBy,sortDir);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response,"Category fetched successfully"));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>>getCategoryById(@PathVariable Long categoryId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(categoryServiceImpl.getCategoryById(categoryId),"Category fetched"));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>>updateCategoryById(@PathVariable Long categoryId,@Valid @RequestBody CategoryRequest request)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(categoryServiceImpl.updateCategoryById(categoryId,request),"Category updated"));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>>deleteCategoryById(@PathVariable Long categoryId)
    {
        categoryServiceImpl.deleteCategoryById(categoryId);
        return ResponseEntity.ok(ApiResponse.success(null, "Category deleted"));
    }
}
