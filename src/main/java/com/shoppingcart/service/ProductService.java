package com.shoppingcart.service;

import com.shoppingcart.dto.requestDto.ProductDto;
import com.shoppingcart.exception.ResourceNotFoundException;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto, Integer cartId) throws ResourceNotFoundException;

    List<ProductDto> findPriceLessThanEqual(Float price) throws ResourceNotFoundException;

    List<ProductDto> findByCategoryOrderByPrice(String category) throws ResourceNotFoundException;

    List<ProductDto> getallProductByCart(Integer cartId) throws ResourceNotFoundException;

    ProductDto updateProduct(ProductDto productDto, Integer productId) throws ResourceNotFoundException;

    ProductDto getProductById(Integer productId) throws ResourceNotFoundException;

    List<ProductDto> getAllProducts() throws ResourceNotFoundException;

    ProductDto getProductByCategory(String category) throws  ResourceNotFoundException;

    void deleteProduct(Integer productId) throws ResourceNotFoundException;
}
