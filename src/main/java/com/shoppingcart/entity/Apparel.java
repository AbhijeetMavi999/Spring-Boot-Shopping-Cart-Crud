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
public class Apparel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer apparelId;
    private String type;
    private String brand;
    private String design;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productDto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Apparel apparel = (Apparel) o;
        return apparelId != null && Objects.equals(apparelId, apparel.apparelId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
