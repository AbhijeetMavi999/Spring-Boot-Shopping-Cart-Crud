package com.shoppingcart.service;

import com.shoppingcart.dto.requestDto.BookDto;
import com.shoppingcart.exception.ResourceNotFoundException;

import java.util.List;

public interface BookService {

    BookDto createBook(BookDto bookDto, Integer productId) throws ResourceNotFoundException;

    List<BookDto> findByGenreOrderByPublicationsDesc(String genre) throws ResourceNotFoundException;

    List<BookDto> searchBooksByPublications(String publication) throws ResourceNotFoundException;

    BookDto getBookByAuthor(String author) throws ResourceNotFoundException;

    List<BookDto> getAllBooks() throws ResourceNotFoundException;
}
