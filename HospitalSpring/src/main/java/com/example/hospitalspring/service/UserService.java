package com.example.hospitalspring.service;

import com.example.hospitalspring.entity.User;
import com.example.hospitalspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByEmail(String email){
        if(userRepository.findUserByEmail(email).isPresent()){
            return userRepository.findUserByEmail(email).get();
        }
        return null;
    }
}
