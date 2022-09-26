package com.shoppingcart.repository;

import com.shoppingcart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    void deleteByCartId(Integer cartId);

    List<Cart> findByQuantityLessThanEqual(Integer quantity);
}
