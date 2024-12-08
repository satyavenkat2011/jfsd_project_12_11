package org.studyeasy.SpringStarter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studyeasy.SpringStarter.Model.User;
import org.studyeasy.SpringStarter.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Method to find a user by their email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Method to check if the user is an admin
    public boolean isAdmin(String email) {
        User user = userRepository.findByEmail(email);
        return user != null && "ADMIN".equals(user.getRole());
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
}
