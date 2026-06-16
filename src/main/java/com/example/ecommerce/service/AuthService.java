package com.example.ecommerce.service;

import com.example.ecommerce.dto.LoginRequest;
import com.example.ecommerce.dto.LoginResponse;
import com.example.ecommerce.entity.RefreshToken;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.RefreshTokenRepository;
import com.example.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Transactional
    public LoginResponse login(
            LoginRequest request
    ){
        User user = userRepository.findByUsername(
                request.username()
        ).orElseThrow(() -> new RuntimeException("Invalid Credentials"));

        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }

        String accessToken = jwtService.generateAccessToken(request.username());

        String refreshToken = jwtService.generateRefreshToken(request.username());

        RefreshToken token = new RefreshToken();

        token.setToken(refreshToken);

        token.setUser(user);

        token.setExpiresAt(LocalDateTime.now().plusDays(30));

        refreshTokenRepository.save(token);

        return new LoginResponse(accessToken, refreshToken);
    }

}
