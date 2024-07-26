package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public Optional<User> getById(Long id){
        return userRepository.findById(id);
    }
    public Optional<User> getByUserName(String username){
        return userRepository.findByUserName(username);
    }

    public boolean userAlreadyExist(String userName){ return userRepository.existsByUserName(userName);}

    public User saveUser(User user){
        return userRepository.save(user);
    }

}
