package com.blog.blog.service;

import com.blog.blog.model.*;
import com.blog.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import java.util.Map;

@Service
@RequiredArgsConstructor

public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final PasswordEncoder passwordEncoder ;


   private final UserRepository userRepository ;


    private final JwtService jwtService ;

    private final AuthenticationManager authenticationManager ;



    public ResponseEntity<?> register( RegisterRequest request) {

        String email = request.getEmail();
        if(userRepository.findByEmail(email).isPresent())
        {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Email already registered");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

//            throw new IllegalArgumentException("Email is Already  registered ");
        }
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        return ResponseEntity.ok(authResponse);
    }

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            System.out.println("Authentication successful");

            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (AuthenticationException ex) {
            System.err.println("Authentication failed: " + ex.getMessage());
            throw new IllegalArgumentException("Authentication failed");
        }
    }


    public void logout(String authorizationHeader) {
        jwtService.addToBlacklist(jwtService.extractTokenFromHeader(authorizationHeader));

    }
}
