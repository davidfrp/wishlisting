package com.davidfrp.wishlisting.service;

import com.davidfrp.wishlisting.model.User;
import com.davidfrp.wishlisting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User getUserFromSession(HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        return userRepository.findById(userId != null ? userId : 0).orElse(null);
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
