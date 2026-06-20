package com.example.ecommerce.controller;

import com.example.ecommerce.dto.*;
import com.example.ecommerce.service.AuthService;
import com.example.ecommerce.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public RefreshTokenResponse refreshToken(
            @RequestBody RefreshTokenRequest request
    ){
        String refreshToken = request.refreshToken();

        if(!jwtService.isTokenValid(refreshToken)){
            throw new RuntimeException("Invalid refresh token");
        }

        String username = jwtService.extractUsername(refreshToken);

        String newAccessToken = jwtService.generateAccessToken(username);

        return new RefreshTokenResponse(newAccessToken);
    }

    @GetMapping("/me")
    public UserResponse me(
            @RequestHeader("Authorization")
            String authHeader
    ){
        return authService.getCurrentUserResponse(authHeader);
    }

}
