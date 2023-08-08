package com.cotiviti.auth.service;

import com.cotiviti.auth.config.JWTUtils;
import com.cotiviti.auth.model.User;
import com.cotiviti.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean validateToken(String token) {
        if (!token.startsWith("Bearer")) {
            return false;
        }
        String jwtToken = token.substring(7);
        String userEmail = jwtUtils.extractEmail(jwtToken);
        if (userEmail == null) {
            return false;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        return jwtUtils.isTokenValid(jwtToken, userDetails);
    }
}

