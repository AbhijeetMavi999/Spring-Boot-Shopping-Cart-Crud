package com.shoppingcart.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer cartId;

    @Column(name = "added_date")
    private Date addedDate;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_amount")
    private Float totalAmount;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userDto;

    @OneToMany(mappedBy = "cartDto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cart cart = (Cart) o;
        return cartId != null && Objects.equals(cartId, cart.cartId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
