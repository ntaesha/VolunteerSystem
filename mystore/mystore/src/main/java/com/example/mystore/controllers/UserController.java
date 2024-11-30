package com.example.mystore.controllers;

import com.example.mystore.models.User;
import com.example.mystore.models.UserDto;
import com.example.mystore.services.UserRepository;
import com.example.mystore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // Signup Form - Show the user registration page
    @GetMapping("/signup") // Corrected mapping
    public String showSignupForm(Model model) {
        System.out.println("Accessing signup page");
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "products/signup"; // returns the "products/signup.html" template
    }

    // Handle Signup - Process user registration
    @PostMapping("/signup") // Corrected mapping
    public String signupUser (@Valid @ModelAttribute UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "products/signup";
        }

        // Check if the email already exists
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            result.rejectValue("email", "error.user", "Email already registered");
            return "products/signup";
        }

        // Create new user and save to the repository
        User user = new User();
        user.setName(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword()); // Ensure password is encrypted
        userRepository.save(user);

        // Redirect to the login page after successful signup
        return "redirect:/products/login"; // After successful signup, redirect to login
    }

    // Login Form - Show the login page
    @GetMapping("/login") // Corrected mapping
    public String showLoginForm() {
        return "products/login"; // returns the "products/login.html" template
    }

    // Handle Login (Spring Security will automatically manage authentication)
    @PostMapping("/login") // Corrected mapping
    public String loginUser () {
        return "products/view-event"; // Redirect to home page after login
    }

    // Logout - Handle user logout (Spring Security will handle this)
    @PostMapping("/logout")
    public String logoutUser () {
        // Invalidate the session and logout user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.clearContext();
        }
        return "redirect:/products/login"; // Redirect to login page after logout
    }

    // View the user profile
    @GetMapping("/profile")
    public String viewProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the logged-in user's name
        User user = userRepository.findByEmail(username); // Fetch user by email (or username)
        model.addAttribute("user", user);
        return "products/profile"; // returns the "products/profile.html"


    }
}