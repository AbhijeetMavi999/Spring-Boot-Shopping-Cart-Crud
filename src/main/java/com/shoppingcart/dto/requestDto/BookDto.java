package com.shoppingcart.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookDto {

    private Integer bookId;
    private String genre;
    private String author;
    private String publications;

    private ProductDto productDto;
}
