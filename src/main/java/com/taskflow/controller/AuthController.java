package com.taskflow.controller;

import com.taskflow.repository.UserRepository;
import com.taskflow.security.JwtService;
import com.taskflow.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {


    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthController(
            UserService userService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public Object register(
            @RequestParam String username,
            @RequestParam String password
    ) {

        return userService.register(username, password);
    }


    @PostMapping("/login")
    public Object login(
            @RequestParam String username,
            @RequestParam String password
    ) {


        var user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado")
                );


        if (!passwordEncoder.matches(
                password,
                user.getPassword()
        )) {

            throw new RuntimeException("Senha inválida");
        }


        String token = jwtService.generateToken(
                user.getUsername()
        );


        return Map.of(
                "token", token
        );
    }
}