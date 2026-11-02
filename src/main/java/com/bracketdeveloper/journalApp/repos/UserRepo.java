package com.bracketdeveloper.journalApp.repos;

import com.bracketdeveloper.journalApp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, String> {
    User findByUsername(String username);
}
