package com.productionPractice1.service;

import com.productionPractice1.dto.request.ProductRequest;
import com.productionPractice1.dto.response.ProductResponse;
import com.productionPractice1.entity.Category;
import com.productionPractice1.entity.Product;
import com.productionPractice1.exception.DuplicateResourceException;
import com.productionPractice1.exception.ResourceNotFoundException;
import com.productionPractice1.repository.CategoryRepository;
import com.productionPractice1.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper,CategoryRepository categoryRepository)
    {
        this.productRepository=productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository=categoryRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest request,Long categoryId) {
        if(productRepository.existsByProductName(request.getProductName()))
           {
               throw new DuplicateResourceException("Product already Exist");
           }
         //getting category
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        //DTO to entity
        Product product=modelMapper.map(request,Product.class);
        //set relationship manually
        product.setCategory(category);
        //saving is necessary
        Product savedProduct=productRepository.save(product);

        return modelMapper.map(savedProduct,ProductResponse.class);
    }

    @Override
    public ProductResponse getAllProduct() {
        return null;
    }
}
