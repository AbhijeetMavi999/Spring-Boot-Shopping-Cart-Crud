package com.shoppingcart.controller;

import com.shoppingcart.dto.requestDto.UserDto;
import com.shoppingcart.dto.responseDto.ApiResponse;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        UserDto registerUser = userService.registerUser(userDto);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer userId) throws ResourceNotFoundException {
        UserDto userDto = userService.getUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUser() throws ResourceNotFoundException {
        List<UserDto> userDtos = userService.getAllUser();
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") Integer userId, @RequestBody UserDto userDto) throws ResourceNotFoundException {
        UserDto updateUser = userService.updateUser(userId, userDto);
        return new ResponseEntity<>(updateUser, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) throws ResourceNotFoundException {
        userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User successfully deleted", true), HttpStatus.OK);
    }
}
