package com.example.ecommerce.controller;

import com.example.ecommerce.dto.LoginRequest;
import com.example.ecommerce.dto.LoginResponse;
import com.example.ecommerce.dto.RefreshTokenRequest;
import com.example.ecommerce.dto.RefreshTokenResponse;
import com.example.ecommerce.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ) {
        String accessToken = jwtService.generateAccessToken(request.username());
        String refreshToken = jwtService.generateRefreshToken(request.username());

        return new LoginResponse(
                accessToken,
                refreshToken
        );
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

}
