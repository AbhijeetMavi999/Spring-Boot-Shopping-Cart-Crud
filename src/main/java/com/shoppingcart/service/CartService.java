package com.shoppingcart.service;

import com.shoppingcart.dto.requestDto.CartDto;
import com.shoppingcart.exception.ResourceNotFoundException;

import java.util.List;

public interface CartService {

    CartDto createCart(CartDto cartDto, Integer userId) throws ResourceNotFoundException;

    List<CartDto> findQuantityLessThanEqual(Integer quantity) throws ResourceNotFoundException;

    CartDto getCart(Integer cartId) throws ResourceNotFoundException;

    void deleteCart(Integer cartId) throws ResourceNotFoundException;

}
