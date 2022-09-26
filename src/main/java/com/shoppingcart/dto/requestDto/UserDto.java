package com.shoppingcart.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    private Integer userId;

    @NotEmpty(message = "Username must not be empty")
    private String userName;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, max = 30)
    private String userPassword;

    @NotEmpty
    @Email(message = "Please provide valid email address", regexp = ".+@.+\\..+")
    private String userEmail;
}
