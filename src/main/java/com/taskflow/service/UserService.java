package com.taskflow.service;

import com.taskflow.model.User;
import com.taskflow.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {


    private final UserRepository repository;
    private final PasswordEncoder encoder;


    public UserService(
            UserRepository repository,
            PasswordEncoder encoder
    ) {
        this.repository = repository;
        this.encoder = encoder;
    }


    public User register(User user) {

        user.setPassword(
                encoder.encode(user.getPassword())
        );

        return repository.save(user);
    }


    public User findByUsername(String username) {

        return repository.findByUsername(username)
                .orElseThrow();
    }

}