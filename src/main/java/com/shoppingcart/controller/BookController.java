package com.shoppingcart.controller;

import com.shoppingcart.dto.requestDto.BookDto;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/create/product/{productId}")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto, @PathVariable("productId") Integer productId) throws ResourceNotFoundException {
        BookDto create = bookService.createBook(bookDto, productId);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }

    @GetMapping("/search/{publication}")
    public ResponseEntity<List<BookDto>> searchByPublications(@PathVariable("publication") String publication) throws ResourceNotFoundException {
        List<BookDto> bookDtos = bookService.searchBooksByPublications(publication);
        return new ResponseEntity<List<BookDto>>(bookDtos, HttpStatus.FOUND);
    }

    @GetMapping("/getbygenre/{genre}")
    public ResponseEntity<List<BookDto>> findByGenreOrderByPublicationsDesc(@PathVariable("genre") String genre) throws ResourceNotFoundException {
        List<BookDto> bookDtos = bookService.findByGenreOrderByPublicationsDesc(genre);
        return new ResponseEntity<List<BookDto>>(bookDtos, HttpStatus.FOUND);
    }

    @GetMapping("/getbyauthor/{author}")
    public ResponseEntity<BookDto> getBookByAuthor(@PathVariable("author") String author) throws ResourceNotFoundException {
        BookDto bookDto = bookService.getBookByAuthor(author);
        return new ResponseEntity<>(bookDto, HttpStatus.FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooksList() throws ResourceNotFoundException {
        List<BookDto> bookDtos = bookService.getAllBooks();
        return new ResponseEntity<List<BookDto>>(bookDtos, HttpStatus.FOUND);
    }
}
