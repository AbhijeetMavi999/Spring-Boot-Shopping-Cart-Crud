package com.shoppingcart.controller;

import com.shoppingcart.dto.requestDto.ProductDto;
import com.shoppingcart.dto.responseDto.ApiResponse;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create new Product
    @PostMapping("/create/cart/{cartId}/post")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, @PathVariable("cartId") Integer cartId) throws ResourceNotFoundException {
        ProductDto productDto1 = productService.createProduct(productDto, cartId);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    @GetMapping("/getbyPrice/{price}")
    public ResponseEntity<List<ProductDto>> findPriceLessThanEqual(@PathVariable("price") Float price) throws ResourceNotFoundException {
        List<ProductDto> productDtos = productService.findPriceLessThanEqual(price);
        return new ResponseEntity<List<ProductDto>>(productDtos ,HttpStatus.FOUND);
    }

    @GetMapping("/getbycategoryasc/{category}")
    public ResponseEntity<List<ProductDto>> findByCategoryOrderByPrice(@PathVariable("category") String category) throws ResourceNotFoundException {
        List<ProductDto> productDtos = productService.findByCategoryOrderByPrice(category);
        return new ResponseEntity<List<ProductDto>>(productDtos, HttpStatus.FOUND);
    }

    @GetMapping("/getbycart/{cartId}")
    public ResponseEntity<List<ProductDto>> getProductByCart(@PathVariable("cartId") Integer cartId) throws ResourceNotFoundException {
        List<ProductDto> productDtos = productService.getallProductByCart(cartId);
        return new ResponseEntity<>(productDtos, HttpStatus.FOUND);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable("productId") Integer productId) throws ResourceNotFoundException {
        ProductDto update = productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") Integer productId) throws ResourceNotFoundException {
        ProductDto product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() throws ResourceNotFoundException {
        List<ProductDto> productDtos = productService.getAllProducts();
        return new ResponseEntity<>(productDtos, HttpStatus.FOUND);
    }

    @GetMapping("/getbycategory/{category}")
    public ResponseEntity<ProductDto> getProductByCategory(@PathVariable("category") String category) throws ResourceNotFoundException {
        ProductDto product = productService.getProductByCategory(category);
        return new ResponseEntity<>(product, HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Integer productId) throws ResourceNotFoundException {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(new ApiResponse("Product Successfully deleted", true), HttpStatus.OK);
    }
}
