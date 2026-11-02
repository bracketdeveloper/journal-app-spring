package com.bracketdeveloper.journalApp.controller;

import com.bracketdeveloper.journalApp.entity.User;
import com.bracketdeveloper.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<User> allUsers = userService.getAllUsers();
        if(allUsers!=null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<User> createEntry(@RequestBody User user) {
        try {
            userService.createUser(user);
            return new ResponseEntity<User>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        Optional<User> userById = userService.getUserById(userId);
        if (userById.isPresent()) return new ResponseEntity<User>(userById.get(), HttpStatus.OK);
        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User userByUsername = userService.getByUsername(username);
        if (userByUsername != null) return new ResponseEntity<User>(userByUsername, HttpStatus.OK);
        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable String userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("username/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User newEntry) {
        User userInDb = userService.getByUsername(username);
        if (userInDb != null) {
            userInDb.setUsername(newEntry.getUsername() != null && !newEntry.getUsername().equals("") ? newEntry.getUsername() : userInDb.getUsername());
            userInDb.setPassword(newEntry.getPassword() != null && !newEntry.getPassword().equals("") ? newEntry.getPassword() : userInDb.getPassword());
            userService.createUser(userInDb);
            return new ResponseEntity<User>(userInDb, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
