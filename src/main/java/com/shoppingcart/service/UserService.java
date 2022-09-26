package com.shoppingcart.service;

import com.shoppingcart.dto.requestDto.UserDto;
import com.shoppingcart.entity.User;
import com.shoppingcart.exception.ResourceNotFoundException;

import java.util.List;

public interface UserService {

    UserDto registerUser(UserDto userDto);

    UserDto getUserById(Integer userId) throws ResourceNotFoundException;

    List<UserDto> getAllUser() throws ResourceNotFoundException;

    UserDto updateUser(Integer userId, UserDto userDto) throws ResourceNotFoundException;

    void deleteUser(Integer userId) throws ResourceNotFoundException;
}
