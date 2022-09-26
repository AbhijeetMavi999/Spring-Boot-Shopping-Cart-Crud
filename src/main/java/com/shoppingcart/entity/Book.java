package com.shoppingcart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;
    private String genre;
    private String author;
    private String publications;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productDto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return bookId != null && Objects.equals(bookId, book.bookId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
