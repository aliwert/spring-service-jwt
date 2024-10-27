package com.alimert.service;

import com.alimert.jwt.AuthResponse;
import com.alimert.jwt.RefreshTokenRequest;

public interface IRefreshTokenService {
    public AuthResponse refreshToken(RefreshTokenRequest request);
}
