package com.store.mystore.services;

import com.store.mystore.models.User;
import com.store.mystore.repositories.UserRepository; // Ensure this is the correct import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Add this annotation to make it a Spring-managed service bean
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Method to get all users
    public List<User> findAllUsers() {
        return userRepository.findAll(); // Return all users
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
    }

    // Method to find a user by ID
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
    }
}
