package com.bracketdeveloper.journalApp.service.impl;

import com.bracketdeveloper.journalApp.entity.User;
import com.bracketdeveloper.journalApp.exception.ResourceNotFoundException;
import com.bracketdeveloper.journalApp.repos.UserRepo;
import com.bracketdeveloper.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Override
    public User createUser(User user) {
        userRepo.save(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> getUserById(String userId) {
        return userRepo.findById(userId);
    }

    @Override
    public void deleteUserById(String userId) {
        if (!userRepo.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User entry not found with ID: " + userId
            );
        }

        userRepo.deleteById(userId);
    }

    public User getByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
