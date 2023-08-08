package com.cotiviti.auth.controller;

import com.cotiviti.auth.config.JWTUtils;
import com.cotiviti.auth.dto.AuthResponseDTO;
import com.cotiviti.auth.model.User;
import com.cotiviti.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody User user) {
        User user1 = authService.getUserByEmail(user.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (user1 != null) {
            String token = jwtUtils.generateToken(user.getEmail());
            return new ResponseEntity<>(new AuthResponseDTO(token, user1.getId(), user1.getEmail(),user1.getUsername()), HttpStatus.OK);
        }
        return null;

    }

    @GetMapping("/validateToken/{token}")
    public ResponseEntity<Boolean> validateToken(@PathVariable String token) {
        if (authService.validateToken(token)) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
    }
}
