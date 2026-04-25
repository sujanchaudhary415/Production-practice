package com.productionPractice1.service;

import com.productionPractice1.dto.request.CategoryRequest;
import com.productionPractice1.dto.response.CategoryResponse;
import com.productionPractice1.entity.Category;
import com.productionPractice1.exception.DuplicateResourceException;
import com.productionPractice1.exception.ResourceNotFoundException;
import com.productionPractice1.repository.CategoryRepository;
import com.productionPractice1.util.PaginationUtil;
import com.productionPractice1.wrapper.PagedResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepo;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepo, ModelMapper modelMapper)
    {
        this.categoryRepo=categoryRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
            if(categoryRepo.existsByCategoryName(request.getCategoryName()))
            {
                throw new DuplicateResourceException("Category already exist");
            }
            Category category=modelMapper.map(request,Category.class);
            return modelMapper.map(categoryRepo.save(category), CategoryResponse.class);
    }

    @Override
    public PagedResponse<CategoryResponse> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("asc")
                         ? Sort.by(sortBy).ascending()
                         : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Category>categoryPage=categoryRepo.findAll(pageable);

        List<CategoryResponse>content=categoryPage.getContent()
                .stream().map(cat->modelMapper.map(cat, CategoryResponse.class)).toList();


        return PaginationUtil.build(categoryPage,content);
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        return modelMapper.map(category, CategoryResponse.class);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategoryById(Long categoryId, CategoryRequest request) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        category.setCategoryName(request.getCategoryName());
        return modelMapper.map(category, CategoryResponse.class);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long categoryId) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        categoryRepo.delete(category);

    }
}
