package com.shoppingcart.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApparelDto {

    private Integer apparelId;
    private String type;
    private String brand;
    private String design;

    private ProductDto productDto;
}
