package com.shoppingcart.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartDto {

    private Integer cartId;
    private Date addedDate;
    private Integer quantity;
    private Float totalAmount;

    private UserDto userDto;
}
