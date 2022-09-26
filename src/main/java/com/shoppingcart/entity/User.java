package com.shoppingcart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    @NotEmpty(message = "Username must not be empty")
    private String userName;

    @Column(name = "user_password")
    @NotEmpty(message = "Password must not be empty")
    private String userPassword;

    @Column(name = "user_email")
    @NotEmpty(message = "Email address must not be empty")
    private String userEmail;

    @OneToOne(mappedBy = "userDto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return userId != null && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
