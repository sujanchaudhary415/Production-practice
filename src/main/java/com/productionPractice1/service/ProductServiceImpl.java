package com.productionPractice1.service;

import com.productionPractice1.dto.request.ProductRequest;
import com.productionPractice1.dto.response.ProductResponse;
import com.productionPractice1.entity.Category;
import com.productionPractice1.entity.Product;
import com.productionPractice1.exception.DuplicateResourceException;
import com.productionPractice1.exception.ResourceNotFoundException;
import com.productionPractice1.repository.CategoryRepository;
import com.productionPractice1.repository.ProductRepository;
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
    @Transactional
    public ProductResponse createProduct(ProductRequest request,Long categoryId) {
        if(productRepository.existsByProductName(request.getProductName()))
           {
               throw new DuplicateResourceException("Product already Exist");
           }
         //getting category and checking category
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
    public PagedResponse<ProductResponse> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir ) {
        Sort sort=sortDir.equalsIgnoreCase("asc")
                          ?Sort.by(sortBy).ascending()
                          :Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product>productPage= productRepository.findAll(pageable);

        List<ProductResponse>content=productPage.getContent().stream().map(pro->modelMapper.map(pro,ProductResponse.class)).toList();
        return PaginationUtil.build(productPage,content);
    }

    @Override
    public PagedResponse<ProductResponse> getProductsByCategory(Long categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        Page<Product>productPage=productRepository.findByCategory_CategoryId(category.getCategoryId(),pageable);

        List<ProductResponse>content=productPage.getContent().stream().map(pro->modelMapper.map(pro,ProductResponse.class)).toList();
        return PaginationUtil.build(productPage,content);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));

        // 2. Update fields manually (safe for JPA)
        product.setProductName(request.getProductName());
        product.setImage(request.getImage());
        product.setDescription(request.getDescription());
        product.setQuantity(request.getQuantity());
        product.setPrice(request.getPrice());
        product.setSpecialPrice(request.getSpecialPrice());

       return modelMapper.map(product,ProductResponse.class);
    }
}
