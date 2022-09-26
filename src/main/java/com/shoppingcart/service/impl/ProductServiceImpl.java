package com.shoppingcart.service.impl;

import com.shoppingcart.dto.requestDto.ProductDto;
import com.shoppingcart.entity.Cart;
import com.shoppingcart.entity.Product;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.repository.CartRepository;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${product-message}")
    private String productNotFoundMessage;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ProductDto createProduct(ProductDto productDto, Integer cartId) throws ResourceNotFoundException {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart not available"));

        Product product = modelMapper.map(productDto, Product.class);
        product.setCartDto(cart);

        Product created = productRepository.save(product);
        return modelMapper.map(created, ProductDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<ProductDto> findPriceLessThanEqual(Float price) throws ResourceNotFoundException {
        List<Product> products = productRepository.findByPriceLessThanEqual(price);
        if(products.isEmpty()) {
            log.error("Product not found by price less than or equal to {}",price);
            throw new ResourceNotFoundException(productNotFoundMessage);
        }
        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        return productDtos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<ProductDto> findByCategoryOrderByPrice(String category) throws ResourceNotFoundException {
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        if(products.isEmpty()) {
            log.error("Product not found by category {}",category);
            throw new ResourceNotFoundException(productNotFoundMessage);
        }
        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        return productDtos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<ProductDto> getallProductByCart(Integer cartId) throws ResourceNotFoundException {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if(cart.isPresent()) {
            List<Product> products = productRepository.findByCartDto(cart);
            List<ProductDto> productWithCart = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
            return productWithCart;
        } else {
            log.error("Product Service: Product not available with Id {}",cartId);
            throw new ResourceNotFoundException(productNotFoundMessage);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @CachePut(cacheNames = "product", key = "#productId")
    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer productId) throws ResourceNotFoundException {
        Product product = productRepository.findByProductId(productId);
        if(product == null) {
            log.error("Product Service: Product not available with Id {}", productId);
            throw new ResourceNotFoundException(productNotFoundMessage);
        }
        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Cacheable(cacheNames = "product", key = "#productId")
    @Override
    public ProductDto getProductById(Integer productId) throws ResourceNotFoundException {
        Product product = productRepository.findByProductId(productId);
        if(product == null) {
            log.error("Product Service: Product not available with Id {}",productId);
            throw new ResourceNotFoundException(productNotFoundMessage);
        }
        return modelMapper.map(product, ProductDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<ProductDto> getAllProducts() throws ResourceNotFoundException {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()) {
            log.error("Product Service: Products not available");
            throw new ResourceNotFoundException("There is no products in database");
        }
        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        return productDtos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ProductDto getProductByCategory(String category) throws ResourceNotFoundException {
        Product product = productRepository.findByCategory(category);
        if(product == null) {
            log.error("Product Service: Product not available with Category {}",category);
            throw new ResourceNotFoundException(productNotFoundMessage);
        }
        return modelMapper.map(product, ProductDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = "product", key = "product.productId")
    @Override
    public void deleteProduct(Integer productId) throws ResourceNotFoundException {
        Product product = productRepository.findByProductId(productId);
        if(product == null) {
            log.error("Product Service: Product not available with Id {}",productId);
            throw new ResourceNotFoundException(productNotFoundMessage);
        }
        productRepository.deleteById(productId);
    }
}
