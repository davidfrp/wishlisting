package com.davidfrp.wishlisting.service;

import com.davidfrp.wishlisting.model.User;
import com.davidfrp.wishlisting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String displayName, String username, String password) {
        return userRepository.save(new User(displayName, username, password));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username) == null;
    }

    public boolean doesUsernameAndPasswordMatch(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password) != null;
    }
}
