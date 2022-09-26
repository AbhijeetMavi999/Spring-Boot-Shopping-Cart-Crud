package com.shoppingcart.controller;

import com.shoppingcart.dto.requestDto.CartDto;
import com.shoppingcart.dto.responseDto.ApiResponse;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/create/user/{userId}/post")
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto, @PathVariable("userId") Integer userId) throws ResourceNotFoundException {
        CartDto cartDto1 = cartService.createCart(cartDto, userId);
        return new ResponseEntity<>(cartDto1, HttpStatus.CREATED);
    }

    @GetMapping("/get/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable("cartId") Integer cartId) throws ResourceNotFoundException {

        CartDto cartDto = cartService.getCart(cartId);
        return new ResponseEntity<CartDto>(cartDto, HttpStatus.FOUND);
    }

    @GetMapping("/getbyquantity/{quantity}")
    public ResponseEntity<List<CartDto>> findQuantityLessThanEqual(@PathVariable("quantity") Integer quantity) throws ResourceNotFoundException {
        List<CartDto> cartDtos = cartService.findQuantityLessThanEqual(quantity);
        return new ResponseEntity<List<CartDto>>(cartDtos, HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable("cartId") Integer cartId) throws ResourceNotFoundException {
        cartService.deleteCart(cartId);
        return new ResponseEntity(new ApiResponse("Cart Successfully deleted", true), HttpStatus.OK);
    }
}
