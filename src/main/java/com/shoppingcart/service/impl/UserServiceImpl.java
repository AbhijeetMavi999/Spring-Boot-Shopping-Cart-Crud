package com.shoppingcart.service.impl;

import com.shoppingcart.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import com.shoppingcart.dto.requestDto.UserDto;
import com.shoppingcart.entity.User;
import com.shoppingcart.repository.UserRepository;
import com.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user-message}")
    private String userNotFoundMessage;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Cacheable(cacheNames = "user", key = "#userId")
    @Override
    public UserDto getUserById(Integer userId) throws ResourceNotFoundException {
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            log.error("User Service: User not available with Id {}",userId);
            throw new ResourceNotFoundException(userNotFoundMessage);
        }
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<UserDto> getAllUser() throws ResourceNotFoundException {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            log.error("User Service: Users not available");
            throw new ResourceNotFoundException(userNotFoundMessage);
        }
        List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @CachePut(cacheNames = "user", key = "#userId")
    @Override
    public UserDto updateUser(Integer userId, UserDto userDto) throws ResourceNotFoundException {
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            log.error("User Service: User not available with Id {}", userId);
            throw new ResourceNotFoundException(userNotFoundMessage);
        }
        user.setUserName(userDto.getUserName());
        user.setUserEmail(userDto.getUserEmail());
        user.setUserPassword(userDto.getUserPassword());

        User updateUser = userRepository.save(user);
        return modelMapper.map(updateUser, UserDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = "user", key = "user.userId")
    @Override
    public void deleteUser(Integer userId) throws ResourceNotFoundException {
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            log.error("User Service: User not available with Id {}",userId);
            throw new ResourceNotFoundException(userNotFoundMessage);
        }
        userRepository.deleteById(userId);
    }
}
