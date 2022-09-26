package com.shoppingcart.repository;

import com.shoppingcart.entity.Apparel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApparelRepository extends JpaRepository<Apparel, Integer> {
    List<Apparel> findByBrand(String brand);

    List<Apparel> findByDesignContaining(String design);
}
