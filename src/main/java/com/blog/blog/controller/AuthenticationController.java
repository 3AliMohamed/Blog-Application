package com.blog.blog.controller;

import com.blog.blog.model.AuthenticateRequest;
import com.blog.blog.model.AuthenticationResponse;
import com.blog.blog.model.RegisterRequest;
import com.blog.blog.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<ResponseEntity<?>> register(
            @RequestBody RegisterRequest request
    )
    {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticateRequest request
    )
    {
        System.out.print("hello form controller ");
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader,
                       HttpServletRequest request) {
        // Extract the token from the Authorization header
//        String jwtToken = extractToken(authorizationHeader);
        service.logout(authorizationHeader);
        return ResponseEntity.ok("Logout successful");
        // Perform logout operations using the extracted token
        // For example, you can blacklist the token or clear session-related data
        // logoutService.logout(jwtToken);
    }
//    @DeleteMapping("/logout")
//    public ResponseEntity<?> logout() {
//        // Logic to blacklist or invalidate the token
//        // You can also log out the user from the current session if applicable
//        // Here, we invalidate the token by blacklisting it
//        service.logout();
//
//        return ResponseEntity.ok("Logout successful");
//    }
}
