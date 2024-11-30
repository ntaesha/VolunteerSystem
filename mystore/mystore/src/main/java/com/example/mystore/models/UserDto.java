package com.example.mystore.models;

import jakarta.validation.constraints.*;

public class UserDto {

    // For Sign Up:

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password should be at least 6 characters long")
    private String password;

    @NotEmpty(message = "Confirm password is required")
    @Size(min = 6, message = "Confirm password should be at least 6 characters long")
    private String confirmPassword;

    // For Login:

    @NotEmpty(message = "Username or Email is required")
    private String loginUsernameOrEmail;

    @NotEmpty(message = "Password is required")
    private String loginPassword;

    // Constructors, Getters, Setters

    public UserDto() {}

    public UserDto(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Getters and Setters for Sign-Up
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    // Getters and Setters for Login
    public String getLoginUsernameOrEmail() {
        return loginUsernameOrEmail;
    }

    public void setLoginUsernameOrEmail(String loginUsernameOrEmail) {
        this.loginUsernameOrEmail = loginUsernameOrEmail;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
