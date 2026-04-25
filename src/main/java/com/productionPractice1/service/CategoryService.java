package com.productionPractice1.service;

import com.productionPractice1.dto.request.CategoryRequest;
import com.productionPractice1.dto.response.CategoryResponse;
import com.productionPractice1.wrapper.PagedResponse;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    PagedResponse<CategoryResponse> getAllCategory(int pageNumber,int pageSize,String sortBy,String sortDir);
    CategoryResponse getCategoryById (Long categoryId);
    CategoryResponse updateCategoryById(Long categoryId,CategoryRequest request);
    void deleteCategoryById(Long categoryId);
}
