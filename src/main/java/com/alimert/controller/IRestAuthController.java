package com.alimert.controller;


import com.alimert.dto.DtoUser;
import com.alimert.jwt.AuthRequest;
import com.alimert.jwt.AuthResponse;
import com.alimert.jwt.RefreshTokenRequest;
import org.springframework.stereotype.Controller;

@Controller
public interface IRestAuthController {
    public DtoUser register(AuthRequest authRequest);

    public AuthResponse authenticate(AuthRequest authRequest);

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
