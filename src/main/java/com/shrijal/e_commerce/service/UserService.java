package com.shrijal.e_commerce.service;

import com.shrijal.e_commerce.dto.request.UserRequestDTO;
import com.shrijal.e_commerce.dto.response.UserResponseDTO;
import com.shrijal.e_commerce.model.Cart;
import com.shrijal.e_commerce.model.User;
import com.shrijal.e_commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for User operations
 * Handles business logic for user registration and management
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Register a new user and also creates an empty cart for the user

    @Transactional
    public UserResponseDTO register(UserRequestDTO dto) {
        // Check if email already exists
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Create new user entity
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());


        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);

        // Save user
        User saved = userRepository.save(user);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());

        return response;
    }

    //Get all users
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserResponseDTO dto = new UserResponseDTO();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
