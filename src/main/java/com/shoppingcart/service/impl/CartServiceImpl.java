package com.shoppingcart.service.impl;

import com.shoppingcart.dto.requestDto.CartDto;
import com.shoppingcart.dto.requestDto.ProductDto;
import com.shoppingcart.entity.Cart;
import com.shoppingcart.entity.Product;
import com.shoppingcart.entity.User;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.repository.CartRepository;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.repository.UserRepository;
import com.shoppingcart.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${cart-message}")
    private String cartNotFoundMessage;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public CartDto createCart(CartDto cartDto, Integer userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not available"));

        Float totalAmount = 0.0f;
        Integer totalQuantity = 0;
        List<Product> product = productRepository.findAll();
        for(Product p: product) {
            totalAmount += p.getPrice();
            totalQuantity++;
        }

        List<Product> products = productRepository.findAll();

        Cart cart = modelMapper.map(cartDto, Cart.class);
        cart.setAddedDate(new Date());
        cart.setUserDto(user);
        cart.setTotalAmount(totalAmount);
        cart.setQuantity(totalQuantity);

        Cart created = cartRepository.save(cart);
        return modelMapper.map(created, CartDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<CartDto> findQuantityLessThanEqual(Integer quantity) throws ResourceNotFoundException {
        List<Cart> carts = cartRepository.findByQuantityLessThanEqual(quantity);
        if(carts.isEmpty()) {
            log.error("Cart Service: Carts not found with quantity less than {}",quantity);
            throw new ResourceNotFoundException(cartNotFoundMessage);
        }

        List<CartDto> cartDtos = carts.stream().map(cart -> modelMapper.map(cart, CartDto.class)).collect(Collectors.toList());
        return cartDtos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Cacheable(cacheNames = "cart", key = "#cartId")
    @Override
    public CartDto getCart(Integer cartId) throws ResourceNotFoundException {
        Optional<Cart> cart = cartRepository.findById(cartId);

        if(cart.isPresent()) {
            return modelMapper.map(cart, CartDto.class);
        } else {
            log.error("Cart Service: Cart not available with Id {}",cartId);
            throw new ResourceNotFoundException(cartNotFoundMessage);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = "cart", key = "cart.cartId")
    @Override
    public void deleteCart(Integer cartId) throws ResourceNotFoundException {
        Optional<Cart> cart = cartRepository.findById(cartId);

        if(cart.isPresent()) {
            log.info("Cart Service: Deleting cart with Id {}", cartId);
            cartRepository.deleteByCartId(cartId);
        } else {
            log.error("Cart Service: Cart not available with Id {}",cartId);
            throw new ResourceNotFoundException(cartNotFoundMessage);
        }
    }
}
