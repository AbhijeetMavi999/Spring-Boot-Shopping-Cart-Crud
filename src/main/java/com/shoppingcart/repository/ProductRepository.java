package com.shoppingcart.repository;

import com.shoppingcart.entity.Cart;
import com.shoppingcart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByCategory(String category);

    List<Product> findByCartDto(Optional<Cart> cart);

    Product findByProductId(Integer productId);

    List<Product> findByPriceLessThanEqual(Float price);

    List<Product> findByCategoryOrderByPriceAsc(String category);
}
