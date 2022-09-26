package com.shoppingcart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private Float price;

    @Column(name = "category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cartDto;

    @OneToMany(mappedBy = "productDto", cascade = CascadeType.ALL)
    List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "productDto", cascade = CascadeType.ALL)
    List<Apparel> apparels = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return productId != null && Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
