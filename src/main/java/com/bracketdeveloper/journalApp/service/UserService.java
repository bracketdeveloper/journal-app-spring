package com.bracketdeveloper.journalApp.service;

import com.bracketdeveloper.journalApp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(String userId);
    void deleteUserById(String userId);

    User getByUsername(String username);
}
