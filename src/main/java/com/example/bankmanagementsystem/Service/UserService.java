package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Api.ApiException;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        return user;
    }

    public void updateUser(Integer id, User updatedUser) {
        User user = getUserById(id);

        User usernameOwner = userRepository.findUserByUsername(updatedUser.getUsername());
        if (usernameOwner != null && !usernameOwner.getId().equals(id)) {
            throw new ApiException("Username already exists");
        }
        if (updatedUser.getEmail() != null && userRepository.existsByEmail(updatedUser.getEmail())
                && !updatedUser.getEmail().equals(user.getEmail())) {
            throw new ApiException("Email already exists");
        }

        user.setUsername(updatedUser.getUsername());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
