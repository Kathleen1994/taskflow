package com.taskflow.controller;

import com.taskflow.dto.AuthRequest;
import com.taskflow.repository.UserRepository;
import com.taskflow.security.JwtService;
import com.taskflow.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<?> register(
            @RequestBody AuthRequest request
    ) {
        if (request.username() == null || request.username().isBlank()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "O username é obrigatório")
            );
        }

        if (request.password() == null || request.password().isBlank()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "A password é obrigatória")
            );
        }

        var user = userService.register(
                request.username(),
                request.password()
        );

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody AuthRequest request
    ) {
        if (request.username() == null || request.username().isBlank()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "O username é obrigatório")
            );
        }

        if (request.password() == null || request.password().isBlank()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "A password é obrigatória")
            );
        }

        var user = userRepository.findByUsername(request.username())
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body(
                    Map.of("error", "Usuário não encontrado")
            );
        }

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )) {
            return ResponseEntity.status(401).body(
                    Map.of("error", "Senha inválida")
            );
        }

        String token = jwtService.generateToken(
                user.getUsername()
        );

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "username", user.getUsername()
                )
        );
    }
}