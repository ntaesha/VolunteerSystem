package com.example.mystore.services;

import com.example.mystore.models.User;
import com.example.mystore.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Fetch all users with sorting
    public List<User> findAll() {
        return userRepository.findAll(Sort.by(Sort.Order.asc("id")));
    }

    // Search for users by name or email
    public List<User> searchByNameOrEmail(String query) {
        return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
    }

    // Register a new user (signup)
    public User registerUser(User user) {
        // You can add password encryption logic here if needed (e.g., BCrypt)
        return userRepository.save(user);
    }

    // Login logic (finding user by name or email)
    public Optional<User> loginUser(String nameOrEmail, String password) {
        // This method should check if the name/email exists and the password matches
        List<User> users = userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(nameOrEmail, nameOrEmail);

        if (users.isEmpty()) {
            return Optional.empty();
        }

        User user = users.get(0); // Assumes unique name/email
        if (user.getPassword().equals(password)) { // Password matching (use BCrypt for real apps)
            return Optional.of(user);
        }

        return Optional.empty();
    }

    // Find a user by id
    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    // Delete a user
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
