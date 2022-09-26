package com.shoppingcart.service.impl;

import com.shoppingcart.dto.requestDto.BookDto;
import com.shoppingcart.dto.requestDto.ProductDto;
import com.shoppingcart.entity.Book;
import com.shoppingcart.entity.Product;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.repository.BookRepository;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BookRepository bookRepository;

    @Value("${book-message}")
    private String bookNotFoundMessage;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BookDto createBook(BookDto bookDto, Integer productId) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not available"));

        Book book = modelMapper.map(bookDto, Book.class);
        book.setProductDto(product);

        book.setGenre(bookDto.getGenre());
        book.setAuthor(bookDto.getAuthor());
        book.setPublications(bookDto.getPublications());

        Book created = bookRepository.save(book);
        return modelMapper.map(created, BookDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<BookDto> findByGenreOrderByPublicationsDesc(String genre) throws ResourceNotFoundException {
        List<Book> books = bookRepository.findByGenreOrderByPublicationsDesc(genre);
        if(books.isEmpty()) {
            log.error("Book Service: Book not found by Genre {}",genre);
            throw new ResourceNotFoundException(bookNotFoundMessage);
        }
        List<BookDto> postDtos = books.stream().map((book -> modelMapper.map(book, BookDto.class))).collect(Collectors.toList());
        return postDtos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<BookDto> searchBooksByPublications(String publication) throws ResourceNotFoundException {
        List<Book> books = bookRepository.findByPublicationsContaining(publication);
        if(books.isEmpty()) {
            log.error("Book Service: Books not available by publication {}",publication);
            throw new ResourceNotFoundException(bookNotFoundMessage);
        }
        List<BookDto> postDtos = books.stream().map((book -> modelMapper.map(book, BookDto.class))).collect(Collectors.toList());
        return postDtos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BookDto getBookByAuthor(String author) throws ResourceNotFoundException {
        Book book = bookRepository.findByAuthor(author);
        if(book == null) {
            log.error("Book Service: Book not available with Author {}",author);
            throw new ResourceNotFoundException(bookNotFoundMessage);
        }
        return modelMapper.map(book, BookDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<BookDto> getAllBooks() throws ResourceNotFoundException {
        List<Book> books = bookRepository.findAll();
        if(books.isEmpty()) {
            log.error("Book Service: Books not available");
            throw new ResourceNotFoundException(bookNotFoundMessage);
        }
        List<BookDto> bookDtos = books.stream().map(book -> modelMapper.map(book, BookDto.class)).collect(Collectors.toList());

        return bookDtos;
    }
}
