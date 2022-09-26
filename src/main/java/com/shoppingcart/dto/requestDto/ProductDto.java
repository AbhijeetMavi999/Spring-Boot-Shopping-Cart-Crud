package com.shoppingcart.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductDto {

    private Integer productId;
    private String productName;
    private Float price;
    private String category;

    private CartDto cartDto;
}
