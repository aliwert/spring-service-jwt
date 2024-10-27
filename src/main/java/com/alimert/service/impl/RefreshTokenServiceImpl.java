package com.alimert.service.impl;


import com.alimert.jwt.AuthResponse;
import com.alimert.jwt.JwtService;
import com.alimert.jwt.RefreshTokenRequest;
import com.alimert.model.RefreshToken;
import com.alimert.model.User;
import com.alimert.repository.RefreshTokenRepository;
import com.alimert.service.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtService jwtService;



    public boolean isRefreshTokenExpired(Date expiredDate) {
        return new Date().before(expiredDate);
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        refreshToken.setUser(user);
        return refreshToken;
    }



    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        Optional<RefreshToken> optional = refreshTokenRepository.findByRefreshToken(request.getRefreshToken());
        if (optional.isEmpty()) {
            System.out.println("token invalid");
        }
        RefreshToken refreshToken = optional.get();
        if (!isRefreshTokenExpired(refreshToken.getExpireDate())) {
            System.out.println("Refresh token expired" + request.getRefreshToken());
        }
        String accessToken = jwtService.generateToken(refreshToken.getUser());
        RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(refreshToken.getUser()));
        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());

    }
}
